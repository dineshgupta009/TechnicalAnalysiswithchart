package com.example.TechnicalAnalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechnicalAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicalAnalysisApplication.class, args);
	}

}
