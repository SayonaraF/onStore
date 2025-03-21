package com.sayonara.core.config;

import com.sayonara.core.dto.CustomerDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // bootstrap
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // key serializer
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // value serializer
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // подтверждение от всех реплик
        props.put(ProducerConfig.RETRIES_CONFIG, 5); // количество попыток
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5); // количество запросов без ответов
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // включение идемпотентности
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.UniformStickyPartitioner"); // использование другого партиционера

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactoryString() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ProducerFactory<String, CustomerDto> producerFactoryJson() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateString() {
        return new KafkaTemplate<String, String>(producerFactoryString());
    }

    @Bean
    public KafkaTemplate<String, CustomerDto> kafkaTemplateJson() {
        return new KafkaTemplate<String, CustomerDto>(producerFactoryJson());
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("on-store-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic customerTopic() {
        return new NewTopic("customer-topic", 10, (short) 1);
    }

    @Bean
    public NewTopic manyCustomersTopic() {
        return new NewTopic("ten-customer-topic", 10, (short) 1);
    }
}
