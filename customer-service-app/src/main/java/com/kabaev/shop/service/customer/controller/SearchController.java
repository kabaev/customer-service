package com.kabaev.shop.service.customer.controller;

import com.kabaev.shop.service.customer.domain.Product;
import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.dto.ProductDtoList;
import com.kabaev.shop.service.customer.dto.SearchRequestDto;
import com.kabaev.shop.service.customer.repository.ElasticSearchRepository;
import com.kabaev.shop.service.customer.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/search/products")
@Slf4j
public class SearchController {

    private final ElasticSearchRepository elasticSearchRepository;
    private final SearchService searchService;

    public SearchController(
            ElasticSearchRepository elasticSearchRepository,
            SearchService searchService) {
        this.elasticSearchRepository = elasticSearchRepository;
        this.searchService = searchService;
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
    public ProductDto getProductByCode(@PathVariable final String code) {
        return searchService.getProductByCode(code);
    }

    @PostMapping("/filter")
    public List<ProductDto> searchWithFilter(@RequestBody final SearchRequestDto dto) {
        return searchService.search(dto);
    }

}
