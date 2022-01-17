package com.workmotion.service;

import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.model.Employee;


public interface EmployeeService {

	Employee createEmployee(Employee employee);
	Employee updateEmployeeState(String id, EmployeeStateChangeEvent employeeStateChangeEvent);
	Employee getEmployee(String employeeID);
	
}
