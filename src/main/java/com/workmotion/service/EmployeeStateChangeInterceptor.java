package com.workmotion.service;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.workmotion.entity.EmployeeEntity;
import com.workmotion.model.EmployeeState;
import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EmployeeStateChangeInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeStateChangeEvent>{

	private final EmployeeRepository employeeRepositry;
	
	@Override
	public void preStateChange(State<EmployeeState, EmployeeStateChangeEvent> state,
			Message<EmployeeStateChangeEvent> message, Transition<EmployeeState, EmployeeStateChangeEvent> transition,
			StateMachine<EmployeeState, EmployeeStateChangeEvent> stateMachine) {


		Optional.ofNullable(message).ifPresent(msg -> {
			Optional.ofNullable(message.getHeaders().getOrDefault("employee_id", "-1"))
			.ifPresent(employeeId -> {
				EmployeeEntity employeeEntity = employeeRepositry.getById((String) employeeId);
				employeeEntity.setState(state.getId());
				employeeRepositry.save(employeeEntity);
			});
		});
		
	}
}
