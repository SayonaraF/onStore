package com.sayonara.core.kafka;

import com.sayonara.core.dto.CustomerDto;
import com.sayonara.core.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private KafkaTemplate<String, CustomerDto> customerKafkaTemplate;
    private KafkaTemplate<String, String> kafkaTemplate;
    private CustomerService customerService;

    public void send(String message) {
        kafkaTemplate.send("on-store-topic", UUID.randomUUID().toString(), message);
    }

    public void sendRandomCustomer() {
        CustomerDto customer = customerService.getRandomCustomer();

        customerKafkaTemplate.send("customer-topic", customer.getId().toString(), customer);
    }

}
