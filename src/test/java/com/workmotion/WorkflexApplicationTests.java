package com.workmotion;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.workmotion.entity.EmployeeEntity;
import com.workmotion.model.EmployeeState;
import com.workmotion.repository.EmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class WorkflexApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepositry;
	
	@Autowired
	private MockMvc mockMvc;


	@Test
	public void givenNoEmployeeExists_whenPostEmployee_thenCreateEmpployee() throws Exception {
		

		
		ResultActions response = mockMvc
							.perform(MockMvcRequestBuilders.post("/employee")           
							.contentType(MediaType.APPLICATION_JSON)
							.content("{\n"
									+ "    \"id\": \"1\",\n"
									+ "    \"firstName\": \"Vinod\",\n"
									+ "    \"lastName\": \"L P\",\n"
									+ "    \"state\": \"INCHECK\"\n"
									+ "}") 
							);
		          
		
		response.andExpect(MockMvcResultMatchers.status().isOk());		
		response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is("Vinod")));
		
		
	}	
	@Test
	public void givenEmployeeExists_whenGetEmployee_thenListSpecificEmployee() throws Exception {
		
		//create employee
		EmployeeEntity employeeEntity = new EmployeeEntity().builder().
				firstName("Vinod").lastName("Test2").build();
		employeeRepositry.save(employeeEntity);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk());		
		response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is("Vinod")));
		
		
	}

	
	@Test
	public void givenEmployeeExistsInStateAdded_whenPostEmployeeEventToINCHECK_thenEmployeeStateChangeToINCHECK() throws Exception {
		
		//create employee
		EmployeeEntity employeeEntity = new EmployeeEntity().builder().
				firstName("Vinod").lastName("L P").state(EmployeeState.ADDED).build();
		employeeRepositry.save(employeeEntity);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/incheck_event"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk());		
		response.andExpect(MockMvcResultMatchers.jsonPath("$.state",CoreMatchers.is("INCHECK")));
		
		
	}
	
	@Test
	public void givenEmployeeExistsInStateAdded_whenPostEmployeeEventoApprove_thenShouldChangeTheState() throws Exception {
		//create employee
		EmployeeEntity employeeEntity = new EmployeeEntity().builder().
				firstName("Vinod").lastName("L P").state(EmployeeState.ADDED).build();
		employeeRepositry.save(employeeEntity);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/APPROVED_EVENT"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk());		
		response.andExpect(MockMvcResultMatchers.jsonPath("$.state",CoreMatchers.is("ADDED")));
	
		
	}
	
	@Test
	public void givenEmployeeExistsInStateAdded_whenPostInvalidEmployeeEven_thenGetError() throws Exception {
		//create employee
		EmployeeEntity employeeEntity = new EmployeeEntity().builder().
				firstName("Vinod").lastName("L P").state(EmployeeState.ADDED).build();
		employeeRepositry.save(employeeEntity);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/test"));
		
		response.andExpect(MockMvcResultMatchers.status().is(400));		
		response.andExpect(MockMvcResultMatchers.jsonPath("$",CoreMatchers.is("Invalid Event")));
	
		
	}	
		

}
