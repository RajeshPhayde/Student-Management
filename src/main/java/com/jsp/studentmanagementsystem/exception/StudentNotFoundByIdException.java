package com.jsp.studentmanagementsystem.exception;

import lombok.Data;

@Data
public class StudentNotFoundByIdException extends RuntimeException{

	private String message;

	public StudentNotFoundByIdException(String message) {
		super();
		this.message = message;
	}
	
}
