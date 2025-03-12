package com.sayonara.core.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sayonara.core.CoreApplication;
import com.sayonara.core.dto.CustomerDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CoreApplication.class)
@DirtiesContext
@EmbeddedKafka
class KafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    private final BlockingQueue<ConsumerRecord<String, CustomerDto>> queue = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "customer-topic", groupId = "test_consumer")
    public void listen(ConsumerRecord<String, CustomerDto> record) {
        queue.add(record);
    }

    @Test
    public void sendingRandomCustomer_shouldSendingCustomer_thenMessageReceived() throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        kafkaProducer.sendRandomCustomer();

        ConsumerRecord<String, CustomerDto> record = queue.poll(5, TimeUnit.SECONDS);

        assertNotNull(record);
        assertNotNull(record.value());
    }
}