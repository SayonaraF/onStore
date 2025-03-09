package com.sayonara.core.util.scheduler;

import com.sayonara.core.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SendKafkaMessageJob {

    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "${interval_in_cron}")
    @Async
    public void sendMessageToKafka() throws InterruptedException {
        log.info("{} start", Thread.currentThread().getName());
        Thread.sleep(5000);

        log.info("Sending kafka topic message");
        kafkaProducer.send("Kafka scheduler is working");
        log.info("Message sent");
    }

    @Scheduled(cron = "${interval_in_cron}")
    @Async
    public void recordingLog() throws InterruptedException {
        log.info("{} start", Thread.currentThread().getName());
        Thread.sleep(5000);

        log.info("recording log from LogEntryScheduler");
    }

}
