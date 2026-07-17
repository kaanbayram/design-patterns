package com.example.patterns;

import org.springframework.boot.SpringApplication;

public class TestPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.from(PatternsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
