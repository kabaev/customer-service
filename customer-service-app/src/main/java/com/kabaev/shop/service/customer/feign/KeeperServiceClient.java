package com.kabaev.shop.service.customer.feign;

import com.kabaev.shop.service.customer.dto.ProductDtoList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "keeperService", url = "http://localhost:8080/api/v1/products")
public interface KeeperServiceClient {

    @GetMapping
    public ProductDtoList getAllProducts();

}
