package com.jsp.studentmanagementsystem.util;

import lombok.Data;

@Data
public class ResponseStrucure<T> {
	private int status;
	private String message;
	private T data;

}
