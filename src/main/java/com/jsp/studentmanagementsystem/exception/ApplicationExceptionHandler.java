package com.jsp.studentmanagementsystem.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String, String> errors = new HashMap<String, String>();
		
		for(ObjectError error : allErrors) {
			FieldError fieldError = (FieldError) error;
			String message = fieldError.getDefaultMessage();
			String field = fieldError.getField();
			errors.put(field, message);
		}
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StudentNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure> studentNotFoundById(StudentNotFoundByIdException exception, HttpServletRequest req){
		ErrorStructure error = new ErrorStructure();
		error.setMessage(exception.getMessage());
		error.setUrl(req.getRequestURI());
		error.setRouteCause("The Student is not present with the requested Id!!!");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StudentNotFoundByEmailException.class)
	public ResponseEntity<ErrorStructure> studentNotFoundByEmail(StudentNotFoundByEmailException exception, HttpServletRequest req){
		ErrorStructure error = new ErrorStructure();
		error.setMessage(exception.getMessage());
		error.setUrl(req.getRequestURI());
		error.setRouteCause("The Student is not present with the requested email!!!");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StudentNotFoundByPhNoException.class)
	public ResponseEntity<ErrorStructure> studentNotFoundByPhNo(StudentNotFoundByPhNoException exception, HttpServletRequest req){
		ErrorStructure error = new ErrorStructure();
		error.setMessage(exception.getMessage());
		error.setUrl(req.getRequestURI());
		error.setRouteCause("The Student is not present with the requested PhNo!!!");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<ErrorStructure>(error, HttpStatus.NOT_FOUND);
	}
//	
	
}
