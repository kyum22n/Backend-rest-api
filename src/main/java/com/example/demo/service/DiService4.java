package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DiDaoInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiService4 {
  //필드 주입 -----------------------------------------------
  // @Autowired
  // @Qualifier("diDaoInterfaceImpl2")
  // private DiDaoInterface diDaoInterface;

  // public DiService4() {
  //   log.info("실행");
  // }
  //--------------------------------------------------------

  // 생성자 주입 ---------------------------------------------
  private DiDaoInterface diDaoInterface;

  public DiService4(@Qualifier("diDaoInterfaceImpl1") DiDaoInterface diDaoInterface) {
    this.diDaoInterface = diDaoInterface;
  }
  //--------------------------------------------------------

  public void add() {
    log.info("실행");
    diDaoInterface.insert();
  }
}
