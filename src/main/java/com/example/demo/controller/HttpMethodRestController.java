package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/http-rest")
public class HttpMethodRestController {
	@GetMapping("/get-method1")
	//@RequestMapping(value="/get-method1", method=RequestMethod.GET)
	public String getMethod1() {
		return "응답 내용";
	}
	
	@GetMapping(value="/get-method2")
	public Board getMethod2() {
		log.info("실행");

		Board board = new Board();
		board.setBno(1);
		board.setBtitle("제목1");
		board.setBcontent("내용1");
		board.setBhitcount(1);
		board.setBwriter("글쓴이1");
		board.setBdate(new Date());

		return board;
	}
	
	@PostMapping(value="/post-method")
	public List<Board> postMethod() {
		List<Board> list = new ArrayList<>();
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setBno(i);
			board.setBtitle("제목" + i);
			board.setBcontent("내용" + i);
			board.setBhitcount(i);
			board.setBwriter("글쓴이" + i);
			board.setBdate(new Date());
			list.add(board);
		}
		return list;
	}
	
	@PutMapping(value="/put-method")
	public Map<String, String> putMethod() {
		Map<String, String> map = new HashMap<>();
		map.put("result", "수정 성공");
		return map;
	}
	
	@DeleteMapping("/delete-method")
	public Map<String, String> deleteMethod() {
		Map<String, String> map = new HashMap<>();
		map.put("result", "삭제 성공");
		return map;
	}
	
	@GetMapping(
		value="/content-nego",
		produces= {"application/json", MediaType.APPLICATION_XML_VALUE}
	)
	public List<Board> contentNego() {
		List<Board> list = new ArrayList<>();
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setBno(i);
			board.setBtitle("제목" + i);
			board.setBcontent("내용" + i);
			board.setBhitcount(i);
			board.setBwriter("글쓴이" + i);
			board.setBdate(new Date());
			list.add(board);
		}
		return list;
	}
}









