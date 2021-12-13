package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

	@GetMapping("/")
	public String HellotoOpenShift() {
		return "Welcome to Openshift Deployment";
	}

	@GetMapping("/{name}")
	public String HellotoOpenShift(@PathVariable String name) {
		return "Hi " + name + ", Welcome to Openshift Deployment";
	}

}
