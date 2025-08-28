package com.example.demo.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Board {
	//클라이언트 <-- 서버
	private int bno;
	private Date bdate;
	private int bhitcount;

	//클라이언 <--> 서버
	private String btitle;
	private String bcontent;
	private String bwriter;

	//클라이언트 --> 서버
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MultipartFile battach;

	//서버 --> DB
	private String battachoname;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String battachsname;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String battachtype;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private byte[] battachdata;
}
