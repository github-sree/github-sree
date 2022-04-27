package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class SpringAppApplication {

	Logger logger = LoggerFactory.getLogger(SpringAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

	@GetMapping("/")
	public String HellotoOpenShift() {
		logger.info("triggered {}", new Date());
		return "Openshift Deployment-Edited-now>>>" + new Date();
	}

	@GetMapping("/{name}")
	public String HellotoOpenShift(@PathVariable String name) {
		logger.info("triggered {},{}", name, new Date());
		return "Hi " + name + ", Openshift Deployment changes reflected";
	}

}
