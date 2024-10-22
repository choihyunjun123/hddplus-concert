package com.example.hddplusconcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HddplusConcertApplication {

    public static void main(String[] args) {
        SpringApplication.run(HddplusConcertApplication.class, args);
    }

}
