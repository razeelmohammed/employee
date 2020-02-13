package com.codingtest;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.service.employee.IEmployeeService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SapientApplication {

	@Autowired
	IEmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(SapientApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@PostConstruct
	private void postConstruct() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("employee.csv");
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			try {
				employeeService.save(employeeService.getEmployeeRecords(new File(resource.getFile())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@PreDestroy
	public void onDestroy() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("employee.csv");
		List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees();
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			try {
				File csvOutputFile = new File(resource.getFile());
				try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
					employeeDTOs.stream().forEach(pw::println);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}