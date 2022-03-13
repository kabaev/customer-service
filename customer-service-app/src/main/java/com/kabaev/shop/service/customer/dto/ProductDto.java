package com.kabaev.shop.service.customer.dto;

import com.kabaev.shop.service.customer.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(

        String code,
        String name,
        String description,
        BigDecimal price,
        List<String> imageUriList) {

}
