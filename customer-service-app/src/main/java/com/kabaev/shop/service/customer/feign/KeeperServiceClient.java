package com.kabaev.shop.service.customer.feign;

import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.dto.ProductDtoList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "keeperService", url = "${keeper.service.url}")
public interface KeeperServiceClient {

    @GetMapping
    ProductDtoList getAllProducts();

    @GetMapping(value = "/{code}")
    ProductDto getProductByCode(@PathVariable(value = "code") String code);

}
