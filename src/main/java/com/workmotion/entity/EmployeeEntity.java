package com.workmotion.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workmotion.model.EmployeeState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class EmployeeEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String firstName;
	private String lastName;
	private EmployeeState state;
}
