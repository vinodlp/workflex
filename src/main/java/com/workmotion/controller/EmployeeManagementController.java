package com.workmotion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workmotion.model.Employee;
import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeManagementController {
	

	EmployeeService employeeService;
	

    public EmployeeManagementController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		
		return employeeService.createEmployee(employee);
		
	}
	
	@PostMapping("/{id}/{event}")
	public Employee updateEmployeeState(@PathVariable("id") String employeeID,@PathVariable("event") EmployeeStateChangeEvent employeeStateChangeEvent  ) {
		return employeeService.updateEmployeeState(employeeID,employeeStateChangeEvent);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable("id") String employeeID) {
		return employeeService.getEmployee(employeeID);
	}

}
