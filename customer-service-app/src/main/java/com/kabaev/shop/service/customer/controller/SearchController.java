package com.kabaev.shop.service.customer.controller;

import com.kabaev.shop.service.customer.domain.Product;
import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.dto.ProductDtoList;
import com.kabaev.shop.service.customer.exception.ProductNotFoundException;
import com.kabaev.shop.service.customer.repository.ElasticSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/search/products")
@Slf4j
public class SearchController {

    private final ElasticSearchRepository elasticSearchRepository;

    public SearchController(ElasticSearchRepository elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @GetMapping
    public ProductDtoList getAllProducts() {
        log.debug("Returning all products");
        Iterable<Product> products = elasticSearchRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(new ProductDto(product));
        }
        return new ProductDtoList(productDtoList);
    }

    @GetMapping("/{code}")
    public ProductDto getProductByCode(@PathVariable("code") String code) {
        log.debug("Returning product with code = {}", code);
        Product product = elasticSearchRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the code: " + code));
        return new ProductDto(product);
    }

}
