package com.sayonara.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic topic() {
        return new NewTopic("on-store-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic customerTopic() {
        return new NewTopic("customer-topic", 10, (short) 1);
    }
}
