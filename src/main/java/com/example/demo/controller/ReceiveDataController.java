package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receive-data")
public class ReceiveDataController {
  @GetMapping("/path-variable")
  public String pathVariable() {
      return "receive-data/path-variable";
  }

  @GetMapping("/request-parameter")
  public String requestParameter() {
      return "receive-data/request-parameter";
  }
  
  @GetMapping("/request-body")
  public String requestBody() {
      return "receive-data/request-body";
  }
  
  @GetMapping("/multipart")
  public String multipart() {
      return "receive-data/multipart";
  }
  
  @GetMapping("/header")
  public String header() {
      return "receive-data/header";
  }  
}

















