package com.workmotion.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.workmotion.entity.EmployeeEntity;
import com.workmotion.model.EmployeeState;

@DataJpaTest
class EmployeeRepositoryTest {

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Test
	void testSavingEmployeeRecord() {
		EmployeeEntity employeeEntity = new EmployeeEntity().builder().firstName("Vinod").lastName("L P").state(EmployeeState.ADDED).build();
		
		EmployeeEntity employeeEntityReturned = employeeRepository.save(employeeEntity);
		
		assert(employeeEntity.equals(employeeEntityReturned));
	}

}
