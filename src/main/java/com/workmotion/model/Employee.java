package com.workmotion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	private String id;
	private String firstName;
	private String lastName;
	private EmployeeState state;
	

}
