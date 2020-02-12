package com.codingtest.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.codingtest.helper.ResponseWrapper;
import com.codingtest.helper.StandardResponse;

@ControllerAdvice
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<StandardResponse> handleConflict(RuntimeException ex, WebRequest request) {
		//String bodyOfResponse = "Something went wrong! Please try again (This msg should be meaningful in actual application)";
		return ResponseWrapper.getUnprocessableEntityResponse();
	}
}