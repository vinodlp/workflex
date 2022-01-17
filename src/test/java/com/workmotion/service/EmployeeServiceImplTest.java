package com.workmotion.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.workmotion.entity.EmployeeEntity;
import com.workmotion.model.Employee;
import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceImplTest {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	Employee employee;
	
	@BeforeEach
	void setUp() {
		employee=	Employee.builder()
				.firstName("Vinod")
				.lastName("L P")
				.build();
	}
	/*
	 * @Test void testCreateEmployee() { fail("Not yet implemented"); }
	 */

	@Test
	void testUpdateEmployeeState() {
		employee = employeeService.createEmployee(employee);
		System.out.println(employee);
		employee = employeeService.updateEmployeeState
		(employee.getId(),EmployeeStateChangeEvent.APPROVED_EVENT);
		System.out.println(employee);
		
		

	}
	/*
	 * @Test void testGetEmployee() { fail("Not yet implemented"); }
	 */

}
