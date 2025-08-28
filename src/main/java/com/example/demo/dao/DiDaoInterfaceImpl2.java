package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DiDaoInterfaceImpl2 implements DiDaoInterface {
  public DiDaoInterfaceImpl2() {
    log.info("실행");
  }

  @Override
  public void insert() {
    log.info("DiDaoInterfaceImpl2에서 행 삽입");
  }
}
