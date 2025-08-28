package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DiDaoInterfaceImpl1 implements DiDaoInterface {
  public DiDaoInterfaceImpl1() {
    log.info("실행");
  }

  @Override
  public void insert() {
    log.info("DiDaoInterfaceImpl1에서 행 삽입");
  }
}
