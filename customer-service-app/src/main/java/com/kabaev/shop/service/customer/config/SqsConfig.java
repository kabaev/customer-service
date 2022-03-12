package com.kabaev.shop.service.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    private final String regionName;
    private final String sqsEndpoint;

    public SqsConfig(
            @Value("${aws.region.name}") String regionName,
            @Value("${sqs.endpoint}") String sqsEndpoint) {
        this.regionName = regionName;
        this.sqsEndpoint = sqsEndpoint;
    }

    @Bean
    public SqsClient sqsClient() {
        Region region = Region.of(regionName);
        return SqsClient.builder()
                .endpointOverride(URI.create(sqsEndpoint))
                .region(region)
                .build();
    }

}
