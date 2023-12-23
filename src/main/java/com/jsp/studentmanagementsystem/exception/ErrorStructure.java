package com.jsp.studentmanagementsystem.exception;

import lombok.Data;

@Data
public class ErrorStructure<T> {

	private int status;
	private String message;
	private String routeCause;
	private String url;
}
