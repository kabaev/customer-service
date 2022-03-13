package com.kabaev.shop.service.customer.updater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabaev.shop.service.customer.domain.Product;
import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.feign.KeeperServiceClient;
import com.kabaev.shop.service.customer.repository.ElasticSearchRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@Component
public class ProductUpdateScheduler {

    private final SqsUpdater sqsUpdater;
    private final KeeperServiceClient keeperServiceClient;
    private final ElasticSearchRepository elasticSearchRepository;

    public ProductUpdateScheduler(
            SqsUpdater sqsUpdater,
            KeeperServiceClient keeperServiceClient,
            ElasticSearchRepository elasticSearchRepository) {
        this.sqsUpdater = sqsUpdater;
        this.keeperServiceClient = keeperServiceClient;
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void updateMessage() {
        List<Message> messages = sqsUpdater.pullMessages();
        ObjectMapper mapper = new ObjectMapper();
        messages.forEach(message -> {
            try {
                JsonNode node = mapper.readTree(message.body());
                String code = node.get("Message").toString();
                ProductDto productByCode = keeperServiceClient.getProductByCode(code.substring(1, code.length() - 1));
                System.out.println(productByCode);
                elasticSearchRepository.save(new Product(productByCode));
                sqsUpdater.cleanQueue(messages);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}
