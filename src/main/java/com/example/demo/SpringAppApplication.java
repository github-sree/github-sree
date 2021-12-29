package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@SpringBootApplication
@RestController
public class SpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

	@GetMapping("/")
	public String HellotoOpenShift() {
		return "Welcome to Openshift Deployment-Edited"+new Date();
	}

	@GetMapping("/{name}")
	public String HellotoOpenShift(@PathVariable String name) {
		return "Hi " + name + ", Welcome to Openshift Deployment";
	}

}
