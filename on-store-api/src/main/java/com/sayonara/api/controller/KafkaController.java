package com.sayonara.api.controller;

import com.sayonara.core.dto.CustomerDto;
import com.sayonara.core.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/kafka")
@AllArgsConstructor
public class KafkaController {

    private KafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        log.info("Sending Message: {}", message);
        kafkaProducer.send(message);
        log.info("Message Sent");

        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/customer")
    public CustomerDto getCustomer() {
        return null;
    }

    @PostMapping("/send_customer")
    public ResponseEntity<String> sendCustomer() {
        kafkaProducer.sendRandomCustomer();
        return ResponseEntity.ok("Customer Sent");
    }

}
