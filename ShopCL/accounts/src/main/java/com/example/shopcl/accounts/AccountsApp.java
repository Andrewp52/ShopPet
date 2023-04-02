package com.example.shopcl.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountsApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountsApp.class, args);
    }
}