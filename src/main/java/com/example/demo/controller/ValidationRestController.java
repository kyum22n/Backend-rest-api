package com.example.demo.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Member;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/validation-rest")
public class ValidationRestController {
	// mid=xxx&mname=xxx&mpassword=xxx
	@RequestMapping("/request-parameter")
	public String requestParameter(@ModelAttribute @Valid Member member) {
		return "{\"validation\": \"success\"}";
	}

	// {"mid": "red", "mname": "홍길동", "mpassword": "12345"}
	@PostMapping("/json")
	public String json(@RequestBody @Valid Member member) {
		return "{\"validation\": \"success\"}";
	}
}









