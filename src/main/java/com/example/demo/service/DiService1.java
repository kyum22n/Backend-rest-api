package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DiDao1;
import com.example.demo.dao.DiDao2;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiService1 {
  //필드 주입
  @Autowired
  private DiDao1 diDao1;

  @Autowired
  private DiDao2 diDao2;

  public DiService1() {
    log.info("실행");
  }

  public void add() {
    log.info("실행");
    diDao1.insert();
    diDao2.insert();
  }
}
