package com.kabaev.shop.service.customer.repository;

import com.kabaev.shop.service.customer.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<Product, String> {
}
