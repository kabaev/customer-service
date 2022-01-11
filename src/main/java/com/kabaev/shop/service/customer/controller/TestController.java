package com.kabaev.shop.service.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class TestController {

    /**
     * Return greeting from the customer-service
     *
     * @param name the one to greet
     * @return greeting
     */
    @GetMapping("/{name}")
    public String getGreeting(@PathVariable("name") String name) {
        return String.format("Hello, %s, from the customer-service!", name);
    }

}
