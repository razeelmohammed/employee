package com.codingtest.controller.employee.impl;

import com.codingtest.controller.employee.IEmployeeControllerOpenApi3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codingtest.controller.employee.IEmployeeController;
import com.codingtest.helper.ResponseWrapper;
import com.codingtest.helper.StandardResponse;
import com.codingtest.service.employee.IEmployeeService;

@RestController
public class EmployeeController implements IEmployeeControllerOpenApi3 {

	@Autowired
	IEmployeeService employeeService;

	@Override
	public ResponseEntity<StandardResponse> updateSalaryByPlace(String place, Double percentage) {
		return ResponseWrapper.getResponseEntityForGet(employeeService.updateSalaryByPlace(place, percentage));
	}

	@Override
	public ResponseEntity<StandardResponse> getTotalSalaryBasedOnParam(String param, String value) {
		return ResponseWrapper.getResponseEntityForGet(employeeService.getTotalSalaryBasedOnParam(param, value));
	}

	@Override
	public ResponseEntity<StandardResponse> getEmployeesBasedOnPlace(String place) {
		return ResponseWrapper.getResponseEntityForGet(employeeService.getEmployeesBasedOnPlace(place));
	}

	@Override
	public ResponseEntity<StandardResponse> getSalaryRangeBasedOnTitle(String title) {
		return ResponseWrapper.getResponseEntityForGet(employeeService.getSalaryRangeBasedOnTitle(title));
	}

	@Override
	public ResponseEntity<StandardResponse> getReportingStructure(Integer supervisorid) {
		return ResponseWrapper.getResponseEntityForGet(employeeService.getReportingStructureUsingQuery(supervisorid));
	}

}
