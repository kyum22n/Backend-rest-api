package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dao.mybatis.MemberDao;
import com.example.demo.dto.Member;

@Service
public class MemberService {
  @Autowired
  private MemberDao memberDao;

  // 회원 가입
  public void join(Member member) {
    memberDao.insert(member);
  }

  // 회원 정보 가져오기
  public Member getMember(String mid) {
    Member member = memberDao.selectByMid(mid);
    return member;
  }
  
  // 회원 정보 수정하기
  public Member modify(Member member) {
    Member dbmember = memberDao.selectByMid(member.getMid()); //DB에 저장된 내용 가져오기
    if(dbmember == null) {  
      return null;  //DB에서 가져온 mid가 null이라면 null을 리턴
    } else {
      if(StringUtils.hasText(member.getMname())) {
        dbmember.setMname(member.getMname());
      } 

      if(StringUtils.hasText(member.getMpassword())) {
        dbmember.setMpassword(member.getMpassword());
      }

      if(StringUtils.hasText(member.getMemail())) {
        dbmember.setMemail(member.getMemail());
      }
    }

    memberDao.update(dbmember); //update 메소드 실행하여 수정된 행의 수를 리턴
    dbmember = memberDao.selectByMid(member.getMid());

    return dbmember;
   
  }

  public enum RemoveResult {
    SUCCESS,
    FAIL
  }

  // 회원 삭제하기
  public RemoveResult remove(String mid) {
    int rows = memberDao.delete(mid);

    if(rows == 0) {
      return RemoveResult.FAIL;
    } else {
      return RemoveResult.SUCCESS;
    }
  }
}
