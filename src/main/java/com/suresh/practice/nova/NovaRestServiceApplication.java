package com.suresh.practice.nova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RibbonClient(name = "NOVAMOCKSERVICE")
public class NovaRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaRestServiceApplication.class, args);
	}

}
