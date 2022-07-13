package com.social.moinda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.social.moinda")
public class MoindaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoindaApplication.class, args);
    }
}
