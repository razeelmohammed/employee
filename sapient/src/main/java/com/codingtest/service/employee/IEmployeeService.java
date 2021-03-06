package com.codingtest.service.employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.codingtest.dto.employee.EmployeeDTO;

public interface IEmployeeService {

	Integer save(List<EmployeeDTO> employeeDTOs);

	List<EmployeeDTO> getAllEmployees();

	Double getTotalSalaryBasedOnParam(String param, String value);

	List<EmployeeDTO> getEmployeesBasedOnPlace(String place);
	
	List<EmployeeDTO> getEmployeeRecords(File file) throws IOException;
	
	List<EmployeeDTO> getReportingStructureUsingStream(Integer supervisorId);
	
	List<EmployeeDTO> getReportingStructureUsingQuery(Integer supervisorId);

	String getSalaryRangeBasedOnTitle(String title);

	List<EmployeeDTO> updateSalaryByPlace(String place, Double percentage);
}
