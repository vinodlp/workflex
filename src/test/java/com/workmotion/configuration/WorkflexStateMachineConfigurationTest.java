package com.workmotion.configuration;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.workmotion.model.EmployeeStateChangeEvent;
import com.workmotion.model.EmployeeState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class WorkflexStateMachineConfigurationTest {

	@Autowired
	StateMachineFactory<EmployeeState, EmployeeStateChangeEvent> workflexFactory;
	
	@Test
	void validateStateMachine() {
		StateMachine<EmployeeState,EmployeeStateChangeEvent> workflexStateMachine = workflexFactory.getStateMachine(UUID.randomUUID());
		workflexStateMachine.start();
		log.info(workflexStateMachine.getState().toString());
		workflexStateMachine.sendEvent(EmployeeStateChangeEvent.INCHECK_EVENT);
		log.info(workflexStateMachine.getState().toString());
		workflexStateMachine.sendEvent(EmployeeStateChangeEvent.INCHECK_EVENT);
		log.info(workflexStateMachine.getState().toString());
	}

}
