package com.kabaev.shop.service.customer.controller;

import com.kabaev.shop.service.customer.updater.SqsUpdater;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@RestController
@RequestMapping("/")
public class HealthController {

    private final SqsUpdater updater;

    public HealthController(SqsUpdater updater) {
        this.updater = updater;
    }

    @GetMapping("/pull")
    public List<String> pullMessages() {
        return updater.pullMessages().stream().map(Message::toString).toList();
    }

}
