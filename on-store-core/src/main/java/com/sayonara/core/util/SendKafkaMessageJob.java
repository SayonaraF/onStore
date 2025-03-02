package com.sayonara.core.util;

import com.sayonara.core.service.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SendKafkaMessageJob {

    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "0 */15 * * * *")
    public void sendMessageToKafka() {
        kafkaProducer.send("Kafka scheduler is working");
    }

}
