package com.fwutech.oss.cdpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CdpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdpApiApplication.class, args);
    }

}
