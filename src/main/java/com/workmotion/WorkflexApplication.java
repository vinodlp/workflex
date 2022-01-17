package com.workmotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employee Management API", version = "1.0", description = "Employees Management"))
public class WorkflexApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflexApplication.class, args);
	}

}
