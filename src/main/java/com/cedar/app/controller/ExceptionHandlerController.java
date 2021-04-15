package com.cedar.app.controller;

import java.net.ConnectException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.cedar.app.dto.WebError;
import com.cedar.app.utils.UserNamePasswordWrongException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(value = {ConnectException.class ,RuntimeException.class,UserNamePasswordWrongException.class,Exception.class})
	public ResponseEntity<Object> handleUserNotFound(Exception exception,WebRequest request)
	{
		WebError error = new WebError();
		error.setMethod("REQUEST");
		error.setResult(exception.getMessage());
		return new ResponseEntity<Object>(error,new HttpHeaders(), HttpStatus.CONFLICT);	
	}

}
