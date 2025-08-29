package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {
	// @NotBlank(message = "mid는 필수 사항입니다.")
	// @Size(min = 5, max = 10, message = "mid는 5자 이상 10자 이하이어야 합니다.")
	private String mid;

	// @NotBlank(message = "mname는 필수 사항입니다.")
	private String mname;

	// @NotBlank(message = "mpassword는 필수 사항입니다.")
	// @Size(min = 5, max = 10, message = "mid는 5자 이상 10자 이하이어야 합니다.")
	// @Pattern(
	// 	regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{5,10}$",
	// 	message = "비밀번호는 대소문자를 포함하고, 특수문자를 최소 1개 포함해야 합니다."
  // )
	private String mpassword;
	
	// @NotBlank(message = "memail는 필수 사항입니다.")
	// @Email
	private String memail;

	// @Pattern(
	// 		regexp = "^010-(\\d{3}|\\d{4})-\\d{4}$",
	// 		message = "전화번호는 010-xxx-xxxx 또는 010-xxxx-xxxx 형식이어야 합니다."
	// )
	private String mtel;
	
	// @Pattern(regexp = "^\\d{6}$", message = "mssn은 6자리 숫자여야 합니다.")
	private String mssn;

	private boolean menabled;
	private String mrole;
}
