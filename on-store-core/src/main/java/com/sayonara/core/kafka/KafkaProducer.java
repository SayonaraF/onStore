package com.sayonara.core.kafka;

import com.sayonara.core.dto.CustomerDto;
import com.sayonara.core.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
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
        CustomerDto customerDto = customerService.getRandomCustomer();

        log.info("Producer is working, send object: {}", customerDto);
        customerKafkaTemplate.send("customer-topic", customerDto.getId().toString(), customerDto);
    }

}
