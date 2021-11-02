package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ResourceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceAuthApplication.class, args);
    }
}//spring security
