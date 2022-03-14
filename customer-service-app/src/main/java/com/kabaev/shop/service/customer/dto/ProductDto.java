package com.kabaev.shop.service.customer.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(

        String code,
        String name,
        String description,
        BigDecimal price,
        boolean isDeleted,
        List<String> imageUriList) {

}
