package com.example.shopcl.mailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MailerApp {
    public static void main(String[] args) {
        SpringApplication.run(MailerApp.class, args);
    }
}