package com.codingtest.dao.employee;

import java.util.List;

import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.entity.employee.Employee;

public interface IEmployeeDAO {

	Integer save(List<Employee> employees);

	List<EmployeeDTO> getAllEmployees();

	List<EmployeeDTO> getEmployeesBasedOnPlace(String place);

	List<EmployeeDTO> updateSalaryByPlace(String place, Double percentage);

}
