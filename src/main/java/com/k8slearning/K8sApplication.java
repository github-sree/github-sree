package com.k8slearning;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.service.UserService;

@SpringBootApplication
@RestController
public class K8sApplication {

	Logger logger = LoggerFactory.getLogger(K8sApplication.class);

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(K8sApplication.class, args);
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
