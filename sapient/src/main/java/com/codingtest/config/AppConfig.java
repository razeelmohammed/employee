package com.codingtest.config;

import static org.hibernate.cfg.AvailableSettings.C3P0_ACQUIRE_INCREMENT;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_STATEMENTS;
import static org.hibernate.cfg.AvailableSettings.C3P0_MIN_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_TIMEOUT;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.service.employee.IEmployeeService;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
@PropertySource({ "classpath:db.properties" })
@ComponentScan(basePackages = { "com.codingtest" })
public class AppConfig {

	private @Autowired Environment env;

	@Autowired
	IEmployeeService employeeService;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		// Setting JDBC properties
		props.put(DRIVER, env.getProperty("h2.driver"));
		props.put(URL, env.getProperty("h2.url"));
		props.put(USER, env.getProperty("h2.user"));
		props.put(PASS, env.getProperty("h2.password"));

		// Setting Hibernate properties
		props.put(DIALECT, env.getProperty("hibernate.dialect"));
		props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
		props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
		props.put("hibernate.integrator_provider", "com.erp.shared.helpers.HibernateEventIntegrator");

		// Setting C3P0 properties
		props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
		props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
		props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
		props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
		props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));

		factoryBean.setHibernateProperties(props);
		List<String> packages = new ArrayList<String>();
		packages.add("com.codingtest.entity");
		factoryBean.setPackagesToScan(packages.toArray(new String[] {}));

		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
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
