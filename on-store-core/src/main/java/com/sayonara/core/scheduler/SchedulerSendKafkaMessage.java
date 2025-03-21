package com.sayonara.core.scheduler;

import com.sayonara.core.kafka.KafkaProducer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class SchedulerSendKafkaMessage {

    private final TaskScheduler taskScheduler;
    private final KafkaProducer kafkaProducer;
    private ScheduledFuture<?> scheduledFuture;

    @PostConstruct
    public void scheduleTask() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(this::executeTask, Instant.now(), Duration.ofMinutes(15));
    }

    private void executeTask() {
        kafkaProducer.send("Kafka TaskScheduler is working");
    }

    public void cancelTask() {
        scheduledFuture.cancel(true);
    }

}
