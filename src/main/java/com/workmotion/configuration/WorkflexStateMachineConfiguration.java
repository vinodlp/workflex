package com.workmotion.configuration;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.workmotion.model.EmployeeState;
import com.workmotion.model.EmployeeStateChangeEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableStateMachineFactory
@Configuration
public class WorkflexStateMachineConfiguration extends StateMachineConfigurerAdapter<EmployeeState, EmployeeStateChangeEvent> {

	@Override
		public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeStateChangeEvent> states) throws Exception {
			states.withStates().initial(EmployeeState.ADDED)
			.states(EnumSet.allOf(EmployeeState.class))
			.end(EmployeeState.ACTIVE);
		}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeStateChangeEvent> transitions) throws Exception {
		transitions.withExternal().source(EmployeeState.ADDED).target(EmployeeState.INCHECK).event(EmployeeStateChangeEvent.INCHECK_EVENT)
		.and().withExternal().source(EmployeeState.INCHECK).target(EmployeeState.APPROVED).event(EmployeeStateChangeEvent.APPROVED_EVENT)
		.and().withExternal().source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeStateChangeEvent.ACTIVE_EVENT);
	}
}
