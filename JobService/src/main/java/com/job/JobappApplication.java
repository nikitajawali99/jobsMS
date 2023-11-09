package com.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JobappApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobappApplication.class, args);
	}

}
