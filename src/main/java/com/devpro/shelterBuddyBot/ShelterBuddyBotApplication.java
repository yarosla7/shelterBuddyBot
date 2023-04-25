package com.devpro.shelterBuddyBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.devpro.shelterBuddyBot.dao")
//@ComponentScan(basePackages = "com.devpro")
//@EntityScan("com.devpro.model.shelterBuddyBot.*")
public class ShelterBuddyBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterBuddyBotApplication.class, args);

    }
}
