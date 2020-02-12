package com.codingtest.controller.employee;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.codingtest.SapientApplication;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = SapientApplication.class)
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Ignore
	@Test
	public void updateSalaryByPlace() throws Exception {
//		mockMvc.perform(put("/employee/place/Delhi/salary/10").accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(jsonPath("$.responseObject[0].employeeId", Matchers.is(1)))
//				.andExpect(jsonPath("$.responseObject.*", Matchers.hasSize(5)));
	}

	@Test
	public void getTotalSalaryBasedOnParam() throws Exception {
		mockMvc.perform(get("/employee/BU/Technology").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseObject", Matchers.is(7500.0)));
	}

	@Test
	public void getEmployeesBasedOnPlace() throws Exception {
		mockMvc.perform(get("/employee/Delhi").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseObject[0].employeeId", Matchers.is(3)))
				.andExpect(jsonPath("$.responseObject.*", Matchers.hasSize(3)));
	}

	@Test
	public void getReportingStructure() throws Exception {
		mockMvc.perform(get("/employee/getsubordiates/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseObject[0].employeeId", Matchers.is(2)))
				.andExpect(jsonPath("$.responseObject.*", Matchers.hasSize(4)));

		mockMvc.perform(get("/employee/getsubordiates/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseObject[0].employeeId", Matchers.is(1)))
				.andExpect(jsonPath("$.responseObject.*", Matchers.hasSize(5)));
	}

	@Test
	public void getSalaryRangeBasedOnTitle() throws Exception {
		mockMvc.perform(get("/employee/range/SAL 1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseObject", Matchers.is("Salary ranges between 1000.0 and 1000.0")));
	}
}
