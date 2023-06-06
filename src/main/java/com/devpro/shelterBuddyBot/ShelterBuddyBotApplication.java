package com.devpro.shelterBuddyBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShelterBuddyBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShelterBuddyBotApplication.class, args);
    }
}