package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                GatewayApplication.class, args);
    }
}
//1)browser-->url request-->gateway (对外)
//http://localhost:9000/nacos/provider/echo/sca
//2)gateway->url request-->provider
//http://localhost:8081/provider/echo/sca