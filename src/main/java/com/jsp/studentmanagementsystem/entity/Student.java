package com.jsp.studentmanagementsystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int studentId;
//	AUTO -> hibernate will take care of generating sequence || database will follow single sequence
//	IDENTITY -> Mysql will take care of generating sequence|| each table will have its own sequence
	private String studentName;
	private String studentEmail;
	private long studentPhNo;
	private String studentGrade; 
	private String studentPassword;
}
