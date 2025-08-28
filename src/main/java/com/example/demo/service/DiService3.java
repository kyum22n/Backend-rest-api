package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DiDao1;
import com.example.demo.dao.DiDao2;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiService3 {
  private DiDao1 diDao1;
  private DiDao2 diDao2;

  //Setter 주입
  @Autowired
  public void setDiDao1(DiDao1 diDao1) {
    log.info("실행");
    this.diDao1 = diDao1;
  }

  //Setter 주입
  @Autowired
  public void setDiDao2(DiDao2 diDao2) {
    log.info("실행");
    this.diDao2 = diDao2;
  }

  public void add() {
    log.info("실행");
    diDao1.insert();
    diDao2.insert();
  }
}
