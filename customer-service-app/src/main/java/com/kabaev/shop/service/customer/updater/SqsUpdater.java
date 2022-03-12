package com.kabaev.shop.service.customer.updater;

import com.kabaev.shop.service.customer.feign.KeeperServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Component
public class SqsUpdater {

    private final SqsClient sqsClient;
    private final KeeperServiceClient keeperServiceClient;
    private final String queueUrl;

    public SqsUpdater(
            SqsClient sqsClient,
            KeeperServiceClient keeperServiceClient,
            @Value("${sqs.queue.url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.keeperServiceClient = keeperServiceClient;
        this.queueUrl = queueUrl;
    }

    public List<Message> pullMessages() {
        ReceiveMessageRequest build = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .build();
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(build);
        return receiveMessageResponse.messages();
    }

    public void cleanQueue(List<Message> messagesToDelete) {
        messagesToDelete
                .forEach(message -> {
                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build();
                    sqsClient.deleteMessage(deleteMessageRequest);
                });
    }

}
