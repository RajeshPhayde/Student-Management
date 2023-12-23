package com.jsp.studentmanagementsystem.exception;

import lombok.Data;

@Data
public class StudentNotFoundByEmailException extends RuntimeException{
	
	private String message;

	public StudentNotFoundByEmailException(String message) {
		super();
		this.message = message;
	}
}
