package com.sayonara.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication(scanBasePackages = "com.sayonara.core")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
