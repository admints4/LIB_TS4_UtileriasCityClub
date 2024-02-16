package com.ts4.lib.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LibTs4UtileriasCityClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibTs4UtileriasCityClubApplication.class, args);
    }

}
