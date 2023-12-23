package com.jsp.studentmanagementsystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class StudentRequest {

	@NotNull(message = "Student Name cannot b null !!")
	@NotBlank(message = "Student Name cannot be a empty string !!")
	private String studentName;
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[g][m][a][i][l]+.[c][o][m]", message = "invalid email--Should be in the extension of '@gmail.com' ")
	private String studentEmail;
	@Min(value = 6000000000l, message = "Phone number cannot start below '6' !!!")
	@Max(value = 9999999999l, message = "Phone Number cannot be above '9999999999' !!!")
	private Long studentPhNo;
	private String studentGrade; 
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "8 characters mandatory(1 upperCase,1 lowerCase,1 special Character,1 number)")
	private String studentPassword;

	
/**

@NotNull
@NotBlank ==> " "
@NotEmpty ==> ( Blank & null )
@min
@max
@Email ( regex )
@pattern 

*/
	
	/**
	 * ---------- to validate Email -------------\

	@Email(regexp = "[a-zA-Z0-9+_.-]+@[g][m][a][i][l]+.[c][o][m]", message = "invalid email--Should be in the extension of '@gmail.com' ")



-------------- to validate Password -----------------


	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "8 characters mandatory(1 upperCase,1 lowerCase,1 special Character,1 number)")

	 */
}
