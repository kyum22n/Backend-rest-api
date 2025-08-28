package com.example.demo.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Member;

/*
create table member (
    mid         varchar(20)     primary key,
    mname       varchar(20)     not null,
    mpassword   varchar(200)    not null,
    menabled    number(1,0)     not null,
    mrole       varchar(20)     not null,
    memail      varchar(100)    not null
);
*/

@Mapper
public interface MemberDao {
  public int insert(Member member);
  public Member selectByMid(String mid);
  public int update(Member member);
  public int delete(String mid);
}















