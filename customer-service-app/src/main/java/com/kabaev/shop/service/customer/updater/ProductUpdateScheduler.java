package com.kabaev.shop.service.customer.updater;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@Component
public class ProductUpdateScheduler {

    private SqsUpdater sqsUpdater;

    public ProductUpdateScheduler(SqsUpdater sqsUpdater) {
        this.sqsUpdater = sqsUpdater;
    }

    @Scheduled(fixedDelay = 10000)
    public void updateMessage() {
        List<Message> messages = sqsUpdater.pullMessages();
        messages.forEach(System.out::println);
        sqsUpdater.cleanQueue(messages);
    }

}
