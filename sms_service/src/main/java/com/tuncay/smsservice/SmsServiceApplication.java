package com.tuncay.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class SmsServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(SmsServiceApplication.class, args);
    }


}