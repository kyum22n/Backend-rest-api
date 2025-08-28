package com.example.demo.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/receive-data-rest")
public class ReceiveDataRestController {
	@GetMapping("/path-variable1/{bno}")
	public Map<String, Integer> pathVariable1(@PathVariable("bno") int bno) {
		Map<String, Integer> map = new HashMap<>();
		map.put("bno", bno);
		return map;
	}

	@GetMapping("/path-variable2/{kind}/{bno}")
	public Map<String, Object> pathVariable2(@PathVariable("kind") String kind, @PathVariable("bno") int bno) {
		Map<String, Object> map = new HashMap<>();
		map.put("kind", kind);
		map.put("bno", bno);
		return map;
	}

	// mid=xxx&mname=xxx&mpassword=xxx
	@RequestMapping("/request-parameter1")
	public Map<String, String> requestParameter1(@RequestParam("mid") String mid, @RequestParam("mname") String mname,
			@RequestParam("mpassword") String mpassword) {

		log.info("mid: " + mid);
		log.info("mname: " + mname);
		log.info("mpassword: " + mpassword);

		Map<String, String> map = new HashMap<>();
		map.put("mid", mid);
		map.put("mname", mname);
		map.put("mpassword", mpassword);
		return map;
	}

	// mid=xxx&mname=xxx&mpassword=xxx
	@RequestMapping("/request-parameter2")
	public Map<String, String> requestParameter2(@ModelAttribute Member member) {
		log.info("mid: " + member.getMid());
		log.info("mname: " + member.getMname());
		log.info("mpassword: " + member.getMpassword());

		Map<String, String> map = new HashMap<>();
		map.put("mid", member.getMid());
		map.put("mname", member.getMname());
		map.put("mpassword", member.getMpassword());
		return map;
	}

	// {"mid": "red", "mname": "홍길동", "mpassword": "12345"}
	// <member><mid>red</mid><mname>홍길동</mname><mpassword>12345</mpassword></member>
	@RequestMapping("/json")
	public Map<String, String> json(@RequestBody Member member) {
		log.info("mid: " + member.getMid());
		log.info("mname: " + member.getMname());
		log.info("mpassword: " + member.getMpassword());

		Map<String, String> map = new HashMap<>();
		map.put("mid", member.getMid());
		map.put("mname", member.getMname());
		map.put("mpassword", member.getMpassword());
		return map;
	}

	@PostMapping("/multipart")
	public Map<String, Object> multipart(
			@RequestPart("title") String title,
			@RequestPart("attach") MultipartFile attach) throws Exception {
		log.info("title: {}", title);
		log.info("original-filename: {}", attach.getOriginalFilename());
		log.info("content-type: {}", attach.getContentType());
		
		String saveFilename = new Date().getTime() + "-"  + attach.getOriginalFilename();
		log.info("save-filename: {}", saveFilename);
		
		//방법1
		/*OutputStream os = new FileOutputStream("C:/Temp/" + saveFilename);
		InputStream is = attach.getInputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		BufferedInputStream bis = new BufferedInputStream(is);
		
		byte[] arr = new byte[1024];
		while(true) {
			int readNum = bis.read(arr);
			if(readNum == -1) break;
			bos.write(arr, 0, readNum);
		}
		bos.flush();
		
		bos.close();
		bis.close();*/
		
		//방법2
		attach.transferTo(new File("C:/Temp/" + saveFilename));
		
		//응답 생성
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("originalFilename", attach.getOriginalFilename());
		map.put("saveFilename", saveFilename);
		return map;
	}
	
	@GetMapping("/get-header")
	public String getHeader(@RequestHeader("User-Agent") String userAgent) {
		log.info("User-Agent: {}", userAgent);
		
		String browserKind = null;
		
		if(userAgent.contains("Chrome")) {
			if(userAgent.contains("Edg")) {
				browserKind = "엣지 브라우저";
				
			} else {
				browserKind = "크롬 브라우저";
				
			}
		}
		
		return "브라우저 종류: " + browserKind;
	}
	
	@GetMapping("/set-cookie")
	public String setCookie(HttpServletResponse response) {
		Cookie cookie1 = new Cookie("userName", "hongkildong");
		Cookie cookie2 = new Cookie("userEmail", "hong@mycompany.com");
		
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		
		return "";
	}
	
	@GetMapping("/get-cookie")
	public String getCookie(
			@CookieValue("userName") String userName,
			@CookieValue("userEmail") String userEmail) {
		return "userName: " + userName + ", userEmail:" + userEmail;
	}
}









