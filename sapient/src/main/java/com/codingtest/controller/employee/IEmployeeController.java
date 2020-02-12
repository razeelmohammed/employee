package com.codingtest.controller.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingtest.helper.StandardResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/employee")
@Api(value = "EmployeeAPI", description = "Operations to manipulate employee information")
public interface IEmployeeController {

	@PutMapping("/place/{place}/salary/{percentage}")
	@ApiOperation(value = "Update the salaries of employees based on location", response = StandardResponse.class)
	ResponseEntity<StandardResponse> updateSalaryByPlace(
			@ApiParam("Bangalore or Delhi")  @PathVariable(name = "place") String place,
			@ApiParam("Any relevant number")  @PathVariable(name = "percentage") Double percentage);

	@GetMapping("/{param}/{value}")
	@ApiOperation(value = "Find the total salary based on input parameters.", response = StandardResponse.class)
	ResponseEntity<StandardResponse> getTotalSalaryBasedOnParam(
			@ApiParam("BU - for business unit;PL - for place; SU -for supervisor (number)") @PathVariable(name = "param") String param,
			@ApiParam("Search string(Exact Match)") @PathVariable(name = "value") String value);

	@GetMapping("/{place}")
	@ApiOperation(value = "Find the total salary based on input parameters.", response = StandardResponse.class)
	ResponseEntity<StandardResponse> getEmployeesBasedOnPlace(
			@ApiParam("Enter the place name to get the list of employees") @PathVariable(name = "place") String place);
	
	@GetMapping("/range/{title}")
	@ApiOperation(value = "Find the total salary ranges based on title.", response = StandardResponse.class)
	ResponseEntity<StandardResponse> getSalaryRangeBasedOnTitle(
			@ApiParam("Eg: SAL 1 , SAL 2 etc") @PathVariable(name = "title") String title);
	
	@GetMapping("/getsubordiates/{supervisorid}")
	@ApiOperation(value = "Get subordiates of a supervisor.", response = StandardResponse.class)
	ResponseEntity<StandardResponse> getReportingStructure(
			@ApiParam("Supervisor Id") @PathVariable(name = "supervisorid") Integer supervisorid);

}
