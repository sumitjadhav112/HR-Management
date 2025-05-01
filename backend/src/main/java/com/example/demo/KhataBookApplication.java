package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KhataBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhataBookApplication.class, args);
		System.out.println("Invoice Generator Runs..");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
