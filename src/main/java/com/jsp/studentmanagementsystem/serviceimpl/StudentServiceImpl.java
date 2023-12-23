package com.jsp.studentmanagementsystem.serviceimpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.jsp.studentmanagementsystem.dto.MessageData;
import com.jsp.studentmanagementsystem.dto.StudentRequest;
import com.jsp.studentmanagementsystem.dto.StudentResponse;
import com.jsp.studentmanagementsystem.entity.Student;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByEmailException;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByIdException;
import com.jsp.studentmanagementsystem.exception.StudentNotFoundByPhNoException;
import com.jsp.studentmanagementsystem.repo.StudentRepo;
import com.jsp.studentmanagementsystem.service.StudentService;
import com.jsp.studentmanagementsystem.util.ResponseStrucure;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepo repo;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public ResponseEntity<ResponseStrucure<StudentResponse>> saveStudent(StudentRequest student) {

		Student student1 =  new Student();
		student1.setStudentName(student.getStudentName());
		student1.setStudentEmail(student.getStudentEmail());
		student1.setStudentGrade(student.getStudentGrade());
		student1.setStudentPhNo(student.getStudentPhNo());
		student1.setStudentPassword(student.getStudentPassword());

		Student save = repo.save(student1);

		StudentResponse res = new StudentResponse();
		res.setStudentId(save.getStudentId());
		res.setStudentName(save.getStudentName());
		res.setStudentGrade(save.getStudentGrade());

		ResponseStrucure<StudentResponse> structure = new ResponseStrucure<StudentResponse>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Student data saved successfully");
		structure.setData(res);
		return new ResponseEntity<ResponseStrucure<StudentResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStrucure<StudentResponse>> updateStudent(StudentRequest student, int studentId) {
		Optional<Student> findById = repo.findById(studentId);
		if(findById.isPresent()) {

			Student student1 = findById.get();
			student1.setStudentName(student.getStudentName());
			student1.setStudentEmail(student.getStudentEmail());
			student1.setStudentGrade(student.getStudentGrade());
			student1.setStudentPhNo(student.getStudentPhNo());
			student1.setStudentPassword(student.getStudentPassword());

			Student newStudent = repo.save(student1);

			StudentResponse res = new StudentResponse();
			res.setStudentId(newStudent.getStudentId());
			res.setStudentName(newStudent.getStudentName());
			res.setStudentGrade(newStudent.getStudentGrade());

			ResponseStrucure<StudentResponse> structure = new ResponseStrucure<StudentResponse>();
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Student data updated successfully");
			structure.setData(res);
			return new ResponseEntity<ResponseStrucure<StudentResponse>>(structure, HttpStatus.OK);
		}
		ResponseStrucure<StudentResponse> structure = new ResponseStrucure<StudentResponse>();
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setMessage("Student data unavailable with id : " + studentId);
		structure.setData(null);
		return new ResponseEntity<ResponseStrucure<StudentResponse>>(structure, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<ResponseStrucure<Student>> deleteStudent(int studentId) {
		Optional<Student> findById = repo.findById(studentId);
		if(findById.isPresent()) {
			repo.deleteById(studentId);
			ResponseStrucure<Student> structure = new ResponseStrucure<Student>();
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Student data deleted successfully");
			structure.setData(findById.get());
			return new ResponseEntity<ResponseStrucure<Student>>(structure, HttpStatus.OK);
		}
		else {
			throw new StudentNotFoundByIdException("Failed to delete the student!!!");
		}
	}

	@Override
	public ResponseEntity<ResponseStrucure<Student>> findStudent(int studentId) {
		Optional<Student> findById = repo.findById(studentId);
		if(findById.isPresent()) {
			Student student = findById.get();
			ResponseStrucure<Student> structure = new ResponseStrucure<Student>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student data fetched successfully");
			structure.setData(student);
			return new ResponseEntity<ResponseStrucure<Student>>(structure, HttpStatus.FOUND);			
		}
		else {
			ResponseStrucure<Student> structure = new ResponseStrucure<Student>();
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student data not found with id : " + studentId);
			structure.setData(null);
			return new ResponseEntity<ResponseStrucure<Student>>(structure, HttpStatus.NOT_FOUND);						
		}
	}

	@Override
	public ResponseEntity<List<Student>> findAllStudents() {
		List<Student> findAll = repo.findAll();
		if(findAll.isEmpty()) {
			return new ResponseEntity<List<Student>>(findAll, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Student>>(findAll, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStrucure<List<String>>> getAllEmailsByGrade(String grade) {
		List<String> allEmailsByGrade = repo.getAllEmailsByGrade(grade);
		System.out.println(allEmailsByGrade.toString());

		ResponseStrucure<List<String>> structure = new ResponseStrucure<List<String>>();
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("Student data found with grade : " + grade);
		structure.setData(allEmailsByGrade);
		return new ResponseEntity<ResponseStrucure<List<String>>>(structure, HttpStatus.FOUND);		
	}

	@Override
	public ResponseEntity<ResponseStrucure<Student>> findByStudentEmail(String studentEmail) {
		Student findByStudentEmail = repo.findByStudentEmail(studentEmail);
		if(findByStudentEmail != null) {
			ResponseStrucure<Student> structure = new ResponseStrucure<Student>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student data fetched successfully with email " + studentEmail);
			structure.setData(findByStudentEmail);
			return new ResponseEntity<ResponseStrucure<Student>>(structure, HttpStatus.FOUND);	
		}
		throw new StudentNotFoundByEmailException("Student Email hoikond hoith");
	}

	@Override
	public ResponseEntity<ResponseStrucure<Student>> findByStudentPhNo(String studentPhNo) {
		Student findByStudentPhNo = repo.findByStudentPhNo(studentPhNo);
		if(findByStudentPhNo != null) {
			ResponseStrucure<Student> structure = new ResponseStrucure<Student>();
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Student data fetched successfully with phno " + studentPhNo);
			structure.setData(findByStudentPhNo);
			return new ResponseEntity<ResponseStrucure<Student>>(structure, HttpStatus.FOUND);	
		}
		throw new StudentNotFoundByPhNoException("Student Phno hoikond hoith");
	}

	@Override
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		for(Sheet sheet : workbook) {
			//			System.out.println("in sheet");
			for(Row row : sheet) {
				//				System.out.println("in row");
				if(row.getRowNum()>0) {
					//					String name = row.getCell(0).getStringCellValue();
					//					String email = row.getCell(1).getStringCellValue();
					//					long phoneNumber = (long) row.getCell(2).getNumericCellValue();
					//					String grade = row.getCell(3).getStringCellValue();
					//					String password = row.getCell(4).getStringCellValue();
					//
					//					System.out.println(name +", "+ email +", "+ phoneNumber + ", "+ grade + ", "+ password);
					//				}
					if(row!=null) {
						Student student = new Student();
						student.setStudentName(row.getCell(0).getStringCellValue());
						student.setStudentEmail(row.getCell(1).getStringCellValue());
						student.setStudentPhNo((long)row.getCell(2).getNumericCellValue());
						student.setStudentGrade(row.getCell(3).getStringCellValue());
						student.setStudentPassword(row.getCell(4).getStringCellValue());
						repo.save(student);
					}
				}
			}
		}
		workbook.close();
		return null;
	}

	@Override
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException {
		List<Student> students = repo.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet();

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("StudentId");
		header.createCell(1).setCellValue("StudentName");
		header.createCell(2).setCellValue("StudentEmail");
		header.createCell(3).setCellValue("StudentPhNo");
		header.createCell(4).setCellValue("StudentGrade");
		header.createCell(5).setCellValue("StudentPassword");

		int rowNum = 1;
		for(Student student:students) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(student.getStudentId());
			row.createCell(1).setCellValue(student.getStudentName());
			row.createCell(2).setCellValue(student.getStudentEmail());
			row.createCell(3).setCellValue(student.getStudentPhNo());
			row.createCell(4).setCellValue(student.getStudentGrade());
			row.createCell(5).setCellValue(student.getStudentPassword());
		}
		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
		workbook.close();
		return new ResponseEntity<String> ("Data transfered to Excel sheet!!", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendMail(MessageData messageData) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		message.setText(messageData.getText());
		message.setSentDate(new Date());

		javaMailSender.send(message);
		return new ResponseEntity<String>("Mail sent successfully", HttpStatus.OK);
	}
}
