package com.valerian.evaluacionplataformas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EvaluacionPlataformasApplication {
    public static void main(String[] args) {
        SpringApplication.run(EvaluacionPlataformasApplication.class, args);
    }
}


