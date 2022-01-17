package com.workmotion.service;

import javax.transaction.Transactional;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workmotion.entity.EmployeeEntity;
import com.workmotion.model.Employee;
import com.workmotion.model.EmployeeState;
import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	
	private final EmployeeRepository employeeRepository;
	private final StateMachineFactory<EmployeeState, EmployeeStateChangeEvent> workflexStateMachineFactory;
	private final EmployeeStateChangeInterceptor employeeStateChangeListener;

	
	@Override
	@Transactional
	public Employee createEmployee(Employee employee) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			EmployeeEntity employeeEntity = objectMapper.
					readValue(objectMapper.writeValueAsString(employee), EmployeeEntity.class);
			employeeEntity.setState(EmployeeState.ADDED);
			employeeEntity = employeeRepository.save(employeeEntity);
			employee =  objectMapper.
				readValue(objectMapper.writeValueAsString(employeeEntity), Employee.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return employee;
	}
	
	@Transactional
	private boolean sendEvent(String employeeId, StateMachine<EmployeeState, EmployeeStateChangeEvent> sm, EmployeeStateChangeEvent employeeStateEvent) {
		Message msg = MessageBuilder.withPayload(employeeStateEvent)
						.setHeader("employee_id",employeeId)
						.build();
		return sm.sendEvent(msg);
	}

	@Override
	@Transactional
	public Employee updateEmployeeState(String employeeId, EmployeeStateChangeEvent employeeStateEvent) {
		ObjectMapper objectMapper = new ObjectMapper();
		//employeeEntity.setState(employeeState);
		//employeeEntity = employeeRepository.save(employeeEntity);
		StateMachine<EmployeeState, EmployeeStateChangeEvent> sm = build(employeeId);
		boolean validEvent = sendEvent(employeeId, sm, employeeStateEvent);
		log.info("is " + employeeStateEvent + " valid " + validEvent);
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
		Employee employee = null;
		try {
			employee = objectMapper.
					readValue(objectMapper.writeValueAsString(employeeEntity), Employee.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	@Transactional	
	public Employee getEmployee(String employeeID) {
		log.info("Employee ID " + employeeID);
		ObjectMapper objectMapper = new ObjectMapper();
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).get();
		log.info("Employee Entity " + employeeEntity);
		Employee employee = null;
		try {
			employee = objectMapper.
					readValue(objectMapper.writeValueAsString(employeeEntity), Employee.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}
	
	@Transactional
	private StateMachine<EmployeeState, EmployeeStateChangeEvent> build(String employeeId)
	{
		EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
		StateMachine<EmployeeState, EmployeeStateChangeEvent> sm = 
				workflexStateMachineFactory.getStateMachine(employeeId);
		
		sm.stop();
		sm.getStateMachineAccessor()
		.doWithAllRegions(sma -> {
			sma.addStateMachineInterceptor(employeeStateChangeListener);
			sma.resetStateMachine
			(new DefaultStateMachineContext<EmployeeState, EmployeeStateChangeEvent>
			(employeeEntity.getState(), null, null, null));
		});
			
		sm.start();
		
		return sm;
	}

}
