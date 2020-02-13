package com.codingtest.service.employee.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingtest.constant.BusinessConstant;
import com.codingtest.dao.employee.IEmployeeDAO;
import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.entity.employee.Employee;
import com.codingtest.service.employee.IEmployeeService;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

	@Autowired
	private IEmployeeDAO employeeDAO;

	@Override
	public Integer save(List<EmployeeDTO> employeeDTOs) {
		// Deep copy can be done using gson aswell
		// Gson gson = new Gson();
		List<Employee> employees = employeeDTOs.stream()
				.map(m -> new Employee(m.getEmployeeId(), m.getName(), m.getTitle(), m.getBusinessUnit(), m.getPlace(),
						m.getSupervisor() != null ? new Employee(m.getSupervisor().getEmployeeId()) : null,
						m.getCompetencies(), m.getSalary(), true))
				.collect(Collectors.toList());
		return employeeDAO.save(employees);
	}

	@Override
	public List<EmployeeDTO> updateSalaryByPlace(String place, Double percentage) {
		return employeeDAO.updateSalaryByPlace(place, percentage);
	}

	@Override
	public Double getTotalSalaryBasedOnParam(String param, String value) {
		Double totalSalary = 0.0;
		if (param.equals(BusinessConstant.SEARCH_BUSINESS_UNIT)) {
			totalSalary = employeeDAO.getAllEmployees().stream()
					.filter(p -> p.getBusinessUnit().equalsIgnoreCase(value)).mapToDouble(m -> m.getSalary()).sum();
		} else if (param.equals(BusinessConstant.SEARCH_PLACE)) {
			totalSalary = employeeDAO.getAllEmployees().stream().filter(p -> p.getPlace().equalsIgnoreCase(value))
					.mapToDouble(m -> m.getSalary()).sum();
		} else {
			// if supervisor id is null 0 will be returned
			totalSalary = employeeDAO.getAllEmployees().stream()
					.filter(p -> p.getSupervisorId().toString().equalsIgnoreCase(value)).mapToDouble(m -> m.getSalary())
					.sum();
		}
		return totalSalary;
	}

	@Override
	public String getSalaryRangeBasedOnTitle(String title) {
		// Using DoubleSummaryStatistics insted of reducing seperately
		DoubleSummaryStatistics doubleSummaryStatistics = employeeDAO.getAllEmployees().stream()
				.filter(p -> p.getTitle().equalsIgnoreCase(title)).map(EmployeeDTO::getSalary).mapToDouble(x -> x)
				.summaryStatistics();
		return "Salary ranges between " + doubleSummaryStatistics.getMin() + " and " + doubleSummaryStatistics.getMax();
	}

	@Override
	public List<EmployeeDTO> getEmployeesBasedOnPlace(String place) {
		return employeeDAO.getEmployeesBasedOnPlace(place);
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		return employeeDAO.getAllEmployees();
	}

	@Override
	public List<EmployeeDTO> getReportingStructureUsingQuery(Integer supervisorId) {
		return employeeDAO.getEmployeesByReporting(supervisorId);
	}

	@Override
	public List<EmployeeDTO> getEmployeeRecords(File file) throws IOException {
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		if (file == null)
			return employeeDTOs;
		try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] columns = line.split(",");
				EmployeeDTO employee = new EmployeeDTO(Integer.parseInt(columns[0]), columns[1], columns[2], columns[3],
						columns[4], columns[5].isEmpty() ? null : new EmployeeDTO(Integer.parseInt(columns[5])),
						columns[6], Double.parseDouble(columns[7]));
				employeeDTOs.add(employee);
			}
			return employeeDTOs;
		} catch (Exception e) {
			return employeeDTOs;
		}
	}

	@Override
	public List<EmployeeDTO> getReportingStructureUsingStream(Integer supervisorId) {

// Merge streams
//		List<EmployeeDTO> employees = employeeDAO.getAllEmployees().stream()
//				.filter(p -> p.getEmployeeId() == supervisorId)
//				.flatMap(p -> {   
//				   return	Stream.concat(Stream.of(p),
//						employeeDAO.getAllEmployees().stream()
//								.filter(q -> q.getSupervisorId() == p.getEmployeeId()) ); 
//				}).flatMap(m -> Stream.concat(Stream.of(m),
//						employeeDAO.getAllEmployees().stream()
//						.filter(q -> q.getSupervisorId() == m.getEmployeeId()))  )
//				.collect(Collectors.toList());

		// Recursion required to find the tree
		List<EmployeeDTO> parent = employeeDAO.getAllEmployees().stream().filter(p -> p.getEmployeeId() == supervisorId)
				.collect(Collectors.toList());
		List<EmployeeDTO> mergedList = this.getEmployeeStream(employeeDAO.getAllEmployees(), supervisorId, parent);
		return mergedList;
	}

	private List<EmployeeDTO> getEmployeeStream(List<EmployeeDTO> employees, Integer supervisorId,
			List<EmployeeDTO> mergedEmployees) {
		List<EmployeeDTO> filteredEmployees = employees.stream().filter(p -> p.getSupervisorId() == supervisorId)
				.collect(Collectors.toList());
		mergedEmployees.addAll(filteredEmployees);
		for (EmployeeDTO employeeDTO : filteredEmployees) {
			if (employees.stream().anyMatch(q -> q.getSupervisorId() == employeeDTO.getEmployeeId())) {
				return getEmployeeStream(employees, employeeDTO.getEmployeeId(), mergedEmployees);
			}
		}
		return mergedEmployees;
	}
}
