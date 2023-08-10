package com.mobiusp.backstage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackStageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class, args);
    }

}
