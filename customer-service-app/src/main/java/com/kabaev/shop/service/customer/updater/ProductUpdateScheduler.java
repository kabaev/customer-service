package com.kabaev.shop.service.customer.updater;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabaev.shop.service.customer.domain.Product;
import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.feign.KeeperServiceClient;
import com.kabaev.shop.service.customer.repository.ElasticSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@Component
@Slf4j
public class ProductUpdateScheduler {

    private final SqsUpdater sqsUpdater;
    private final KeeperServiceClient keeperServiceClient;
    private final ElasticSearchRepository elasticSearchRepository;
    private final ObjectMapper objectMapper;

    public ProductUpdateScheduler(
            SqsUpdater sqsUpdater,
            KeeperServiceClient keeperServiceClient,
            ElasticSearchRepository elasticSearchRepository,
            ObjectMapper objectMapper) {
        this.sqsUpdater = sqsUpdater;
        this.keeperServiceClient = keeperServiceClient;
        this.elasticSearchRepository = elasticSearchRepository;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    public void updateMessage() {
        List<Message> messages = sqsUpdater.pullMessages();
        messages.forEach(message -> {
            try {
                SqsMessage sqsMessage = objectMapper.readValue(message.body(), SqsMessage.class);
                ProductDto productByCode = keeperServiceClient.getProductByCode(sqsMessage.message);
                elasticSearchRepository.save(new Product(productByCode));
                sqsUpdater.cleanQueue(messages);
            } catch (JsonProcessingException e) {
                log.error("Unable to parse message with messageId: " + message.messageId(), e);
            }
        });
    }

    static class SqsMessage {

        @JsonProperty("Message")
        private String message;
    }

}
