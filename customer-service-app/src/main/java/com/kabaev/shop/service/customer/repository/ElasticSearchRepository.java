package com.kabaev.shop.service.customer.repository;

import com.kabaev.shop.service.customer.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ElasticSearchRepository extends ElasticsearchRepository<Product, String> {

    Optional<Product> findByCode(String code);

}
