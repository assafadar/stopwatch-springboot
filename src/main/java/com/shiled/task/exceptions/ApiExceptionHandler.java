package com.shiled.task.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * 
 * @author assaf
 * App exception handler, 
 * returns formated error response.
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value= {Exception.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request){
		String body = "Bad reqeust: "+e.getMessage();
		return handleExceptionInternal(e, body, new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}
}
