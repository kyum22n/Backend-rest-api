package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Member;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/db")
@Slf4j
public class DBController {
  @PostMapping("/member-create")
  public String memberCreate(@RequestBody Member member) {
    log.info(member.toString());
    return "회원 등록 성공";
  }

  
  
}
