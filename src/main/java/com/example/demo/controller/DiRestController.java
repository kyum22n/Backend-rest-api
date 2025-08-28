package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DiService1;
import com.example.demo.service.DiService2;
import com.example.demo.service.DiService3;
import com.example.demo.service.DiService4;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/di-rest")
@Slf4j
public class DiRestController {
  @Autowired
  private DiService1 diService1;

  @Autowired
  private DiService2 diService2;

  @Autowired
  private DiService3 diService3;

  @Autowired
  private DiService4 diService4;

  @RequestMapping("/field-di")
  public String fieldDi() {
    log.info("실행");
    diService1.add();
    return "";
  }

  @RequestMapping("/constructor-di")
  public String constructorDi() {
    log.info("실행");
    diService2.add();
    return "";
  }

  @RequestMapping("/setter-di")
  public String setterDi() {
    log.info("실행");
    diService3.add();
    return "";
  }

  @RequestMapping("/named-di")
  public String namedDi() {
    log.info("실행");
    diService4.add();
    return "";
  }
}
