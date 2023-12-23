package com.jsp.studentmanagementsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.util.ResponseStrucure;

public interface StudentService {

	public ResponseEntity<ResponseStrucure<StudentResponse>> saveStudent(StudentRequest student);
	
	public ResponseEntity<ResponseStrucure<StudentResponse>> updateStudent(StudentRequest student, int studentId);
	
	public ResponseEntity<ResponseStrucure<Student>> deleteStudent(int studentId);
	
	public ResponseEntity<ResponseStrucure<Student>> findStudent(int studentId);
	
	public ResponseEntity<List<Student>> findAllStudents();

	public ResponseEntity<ResponseStrucure<List<String>>> getAllEmailsByGrade(String grade);

	public ResponseEntity<ResponseStrucure<Student>> findByStudentEmail(String studentEmail);
	
	public ResponseEntity<ResponseStrucure<Student>> findByStudentPhNo(String studentPhNo);
	
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException;

	public ResponseEntity<String> writeToExcel(String filePath) throws IOException;
	
	public ResponseEntity<String> sendMail(MessageData messageData);
}
