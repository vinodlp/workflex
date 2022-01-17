package com.workmotion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmployeeState {
	
	@JsonProperty("ADDED")
	ADDED,
	@JsonProperty("INCHECK")
	INCHECK,
	@JsonProperty("APPROVED")
	APPROVED,
	@JsonProperty("ACTIVE")
	ACTIVE

}
