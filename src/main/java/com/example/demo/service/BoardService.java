package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.mybatis.BoardDao;
import com.example.demo.dto.Board;
import com.example.demo.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
  @Autowired
  private BoardDao boardDao;

  public List<Board> getListByPage(Pager pager) {
    List<Board> list = boardDao.selectByPage(pager);
    return list;
  }

  public void create(Board board) {
    boardDao.insert(board);
  }
}
