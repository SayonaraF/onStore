package com.sayonara.core.kafka;

import com.sayonara.core.dto.CustomerDto;
import com.sayonara.core.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaProducer {

    private KafkaTemplate<String, CustomerDto> customerKafkaTemplate;
    private KafkaTemplate<String, String> kafkaTemplate;
    private CustomerService customerService;

    public void send(String message) {
        kafkaTemplate.send("on-store-topic", UUID.randomUUID().toString(), message)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Message has been sent: {}, offset: {}",
                                message,
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Message has NOT been sent: {}, offset: {}, exception: {}",
                                message,
                                result.getRecordMetadata().offset(),
                                exception.getMessage());
                    }
                });
    }

    public void sendRandomCustomer() {
        CustomerDto customerDto = customerService.getRandomCustomer();

        log.info("Producer is working, send object: {}", customerDto);
        sendCustomer("customer-topic", customerDto);
    }

    public void sendTenRandomCustomers() {
        List<CustomerDto> customers = IntStream.range(0, 10)
                .mapToObj(i -> customerService.getRandomCustomer())
                .toList();

        log.info("Producer is working, send objects: {}", customers);
        customers.forEach(c -> sendCustomer("ten-customer-topic", c));
    }

    private void sendCustomer(String topic, CustomerDto c) {
        customerKafkaTemplate.send(topic, c.getId().toString(), c)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Customer has been sent with id: {}, offset: {}",
                                c.getId().toString(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Customer has NOT been sent with id: {}, offset: {}, exception: {}",
                                c.getId().toString(),
                                result.getRecordMetadata().offset(),
                                exception.getMessage());
                    }
                });
    }
}