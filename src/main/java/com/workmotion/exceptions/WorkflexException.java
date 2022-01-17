package com.workmotion.exceptions;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WorkflexException {
	   @ExceptionHandler(value = ConversionFailedException.class)
	    public ResponseEntity<String> exception(ConversionFailedException ex) {
	        return new ResponseEntity<>("Invalid Event", HttpStatus.BAD_REQUEST);
	    }
}
