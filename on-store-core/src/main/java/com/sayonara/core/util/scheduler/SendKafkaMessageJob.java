package com.sayonara.core.util.scheduler;

import com.sayonara.core.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendKafkaMessageJob {

    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "${interval_in_cron}")
    public void sendMessageToKafka() {
        kafkaProducer.send("Kafka scheduler is working");
    }

}
