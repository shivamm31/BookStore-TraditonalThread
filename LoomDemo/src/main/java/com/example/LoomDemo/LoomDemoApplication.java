package com.example.LoomDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.LoomDemo")
public class LoomDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoomDemoApplication.class, args);
	}
}
