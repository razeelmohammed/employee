package com.codingtest.dao.employee.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codingtest.dao.employee.IEmployeeDAO;
import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.entity.employee.Employee;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class EmployeeDAO implements IEmployeeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer save(List<Employee> employees) {
		Integer lastRecId = 0;
		for (Employee employee : employees) {
			lastRecId = (Integer) sessionFactory.getCurrentSession().save(employee);
		}
		return lastRecId;
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		String hql = "SELECT E.employeeId AS employeeId,E.title AS title,E.name AS name,E.businessUnit AS businessUnit, "
				+ "E.place AS place, E.competencies AS competencies,COALESCE(S.employeeId,0) AS supervisorId,"
				+ "S.name AS supervisorname,E.salary AS salary "
				+ "FROM Employee E LEFT JOIN E.supervisor S ON E.supervisor.employeeId = S.employeeId WHERE E.active = true";

		// Transformer used for quick implementation. From and above Hibernate 5 the
		// results can be retrieved as streams.
		List<EmployeeDTO> employees = sessionFactory.getCurrentSession().createQuery(hql)
				.setResultTransformer(Transformers.aliasToBean(EmployeeDTO.class)).list();
		return employees.size() > 0 ? employees : new ArrayList<EmployeeDTO>();
	}

	@Override
	public List<EmployeeDTO> getEmployeesBasedOnPlace(String place) {
		String hql = "SELECT E.employeeId AS employeeId,E.title AS title,E.name AS name,E.businessUnit AS businessUnit, "
				+ "E.place AS place, E.competencies AS competencies,COALESCE(S.employeeId,0) AS supervisorId,"
				+ "S.name AS supervisorname,E.salary AS salary "
				+ "FROM Employee E LEFT JOIN E.supervisor S ON E.supervisor.employeeId = S.employeeId WHERE "
				+ "E.place =:place AND E.active = true";

		// Transformer used for quick implementation. From and above Hibernate 5 the
		// results can be retrieved as streams.
		List<EmployeeDTO> employees = sessionFactory.getCurrentSession().createQuery(hql).setParameter("place", place)
				.setResultTransformer(Transformers.aliasToBean(EmployeeDTO.class)).list();
		return employees.size() > 0 ? employees : new ArrayList<EmployeeDTO>();
	}

	@Override
	public List<EmployeeDTO> updateSalaryByPlace(String place, Double percentage) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Employee E SET E.salary = E.salary + ((E.salary/100) * :percentage) "
				+ " WHERE E.place = :place and E.active = true";
		session.createQuery(hql).setParameter("percentage", percentage).setParameter("place", place).executeUpdate();
		return getAllEmployees();
	}

}
