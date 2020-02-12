package com.codingtest.entity.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

	// Ideally BusinessUnit | Place | Competencies should be master entities

	@Id
	@GeneratedValue
	@Column(name = "PK_EMPLOYEE_ID", unique = true, nullable = false)
	private Integer employeeId;

	@Column(name = "VC_TITLE", nullable = false, length = 10)
	private String title;

	@Column(name = "VC_NAME", nullable = false, length = 50)
	private String name;

	@Column(name = "VC_BUSINESS_UNIT", nullable = false, length = 50)
	private String businessUnit;

	@Column(name = "VC_PLACE", nullable = false, length = 50)
	private String place;

	@Column(name = "VC_COMPETENCIES", nullable = false, length = 50)
	private String competencies;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SUPERVISOR_ID")
	private Employee supervisor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supervisor")
	private Set<Employee> subOrdinates = new HashSet<Employee>(0);

	@Column(name = "N_SALARY", nullable = false, length = 10)
	private Double salary;

	@Column(name = "B_ISACTIVE")
	private Boolean active;

	public Employee(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Employee(Integer employeeId, String name, String title, String businessUnit, String place,
			Employee supervisor, String competencies, Double salary, Boolean active) {
		this.employeeId = employeeId;
		this.title = title;
		this.name = name;
		this.businessUnit = businessUnit;
		this.place = place;
		this.competencies = competencies;
		this.supervisor = supervisor;
		this.salary = salary;
		this.active = active;
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

	public Employee getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Employee supervisor) {
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

	public Set<Employee> getSubOrdinates() {
		return subOrdinates;
	}

	public void setSubOrdinates(Set<Employee> subOrdinates) {
		this.subOrdinates = subOrdinates;
	}

}
