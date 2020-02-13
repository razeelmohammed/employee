package com.codingtest.dto.employee;

public class EmployeeDTO {

	private Integer employeeId;
	private String title;
	private String name;
	private String businessUnit;
	private String place;
	private String competencies;
	private EmployeeDTO supervisor;
	private Double salary;
	private Boolean active;

	private Integer supervisorId;
	private String supervisorname;

	public EmployeeDTO() {
	}

	public EmployeeDTO(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeDTO(Integer employeeId, String name, String title, String businessUnit, String place,
			EmployeeDTO supervisor, String competencies, Double salary) {
		this.employeeId = employeeId;
		this.title = title;
		this.name = name;
		this.businessUnit = businessUnit;
		this.place = place;
		this.competencies = competencies;
		this.supervisor = supervisor;
		this.salary = salary;
	}
	
	public EmployeeDTO(Integer employeeId, String name, String title, String businessUnit, String place,
			Integer supervisorId,String supervisorname,String competencies, Double salary) {
		this.employeeId = employeeId;
		this.title = title;
		this.name = name;
		this.businessUnit = businessUnit;
		this.place = place;
		this.competencies = competencies; 
		this.supervisorId = supervisorId; 
		this.supervisorname = supervisorname; 
		this.salary = salary;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCompetencies() {
		return competencies;
	}

	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}

	public EmployeeDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(EmployeeDTO supervisor) {
		this.supervisor = supervisor;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorname() {
		return supervisorname;
	}

	public void setSupervisorname(String supervisorname) {
		this.supervisorname = supervisorname;
	}

	@Override
	public String toString() {
		return employeeId + "," + name + "," + title + "," + businessUnit + "," + place + ","
				+ ((supervisorId == 0) ? "" : supervisorId) + "," + competencies + "," + salary;
	}

}
