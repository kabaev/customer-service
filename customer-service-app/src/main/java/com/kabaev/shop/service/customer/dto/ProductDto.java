package com.kabaev.shop.service.customer.dto;

import com.kabaev.shop.service.customer.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(

        String code,
        String name,
        String description,
        BigDecimal price,
        boolean deleted,
        List<String> imageUriList) {

    public ProductDto(Product product) {
        this(
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isDeleted(),
                product.getImages()
        );
    }

}
