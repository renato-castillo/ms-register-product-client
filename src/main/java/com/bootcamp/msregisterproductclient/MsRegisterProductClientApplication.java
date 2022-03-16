package com.bootcamp.msregisterproductclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsRegisterProductClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRegisterProductClientApplication.class, args);
    }

}
