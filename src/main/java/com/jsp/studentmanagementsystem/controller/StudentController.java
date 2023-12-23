package com.jsp.studentmanagementsystem.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.service.StudentService;
import com.jsp.studentmanagementsystem.util.ResponseStrucure;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService service;
	
	@PostMapping 
	public ResponseEntity<ResponseStrucure<StudentResponse>> saveStudent(@RequestBody @Valid StudentRequest student){
		return service.saveStudent(student);
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<ResponseStrucure<StudentResponse>> updateStudent(@RequestBody StudentRequest student,@PathVariable int studentId){
		return service.updateStudent(student, studentId);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<ResponseStrucure<Student>> findStudent(@PathVariable int studentId){
		return service.findStudent(studentId);
	}
	
	@GetMapping
	public ResponseEntity<List<Student>> findStudents(){
		return service.findAllStudents();
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseEntity<ResponseStrucure<Student>> deleteStudent(@PathVariable int studentId){
		return service.deleteStudent(studentId);
	}
	
	@GetMapping("/{grade}")
	public ResponseEntity<ResponseStrucure<List<String>>> getAllEmailsByGrade(@PathVariable String grade){
		System.out.println(grade);
		return service.getAllEmailsByGrade(grade);
	}
	
	@GetMapping(params = "studentEmail")
	public ResponseEntity<ResponseStrucure<Student>> findByEmail(@RequestParam String studentEmail){
		return service.findByStudentEmail(studentEmail);
	}
	
	@GetMapping(params = "studentPhNo")
	public ResponseEntity<ResponseStrucure<Student>> findByPhNo(@RequestParam String studentPhNo){
		return service.findByStudentPhNo(studentPhNo);
	}
	
	@PostMapping("/extract")
	public ResponseEntity<String> extractDataFronExcel(@RequestParam MultipartFile file) throws IOException{
		return service.extractDataFromExcel(file);
	}
//	@PostMapping("/extract")
//	public ResponseEntity<ResponseStructure<String>> extractDataFromExcel(@RequestParam MultipartFile file) throws IOException
//	{
//		return service.extractDataFromExcel(file);
//	}
	
	@GetMapping("/write/excel")	// can give @PostMapping also
	public ResponseEntity<String> writeToExcel(@RequestParam String filePath) throws IOException{
	return service.writeToExcel(filePath);
	}
	
	@PostMapping("/mail")
	public ResponseEntity<String> sendMail(@RequestBody MessageData messageData){
		return service.sendMail(messageData);
	}
	
}
