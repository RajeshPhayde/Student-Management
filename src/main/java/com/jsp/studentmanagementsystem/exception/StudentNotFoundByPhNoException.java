package com.jsp.studentmanagementsystem.exception;

import lombok.Data;

@Data
public class StudentNotFoundByPhNoException extends RuntimeException {

	private String message;

	public StudentNotFoundByPhNoException(String message) {
		super();
		this.message = message;
	}
	
}
