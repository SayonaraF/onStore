package com.sayonara.core.util;

import com.sayonara.core.service.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendKafkaMessageJob {

    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "0 */15 * * * *")
    public void sendMessageToKafka() {
        kafkaProducer.send("Kafka scheduler is working");
    }

}
