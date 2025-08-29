package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.mybatis.MemberDao;
import com.example.demo.dto.Board;
import com.example.demo.dto.Member;
import com.example.demo.dto.Pager;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;
import com.example.demo.service.MemberService.RemoveResult;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/db")
@Slf4j
public class DBController {
  @Autowired
  private MemberService memberService;

  @Autowired
  private BoardService boardService;

  // 회원 가입
  @PostMapping("/member-create")
  public String memberCreate(@RequestBody Member member) {
    log.info(member.toString());
    memberService.join(member);
    return "회원 등록 성공";
  }

  // 회원 정보 가져오기
  @GetMapping("/member-info")
  public Member memberInfo(@RequestParam("mid") String mid) {
    Member member = memberService.getMember(mid);
    return member;
  }
    
  // 회원 정보 수정하기
  @PutMapping("member-modify")
  public Map<String, Object> memberModify(@RequestBody Member member) {
    Member dbMember = memberService.modify(member);

    Map<String, Object> map = new HashMap<>();
    if(dbMember == null) {
      map.put("result", "fail");
    } else {
      map.put("result", "success");
      map.put("member", dbMember);
    }

    return map;
  }

  // 회원 삭제
  @DeleteMapping("/member-remove/{mid}")
  public String memberRemove(@PathVariable("mid") String mid) {
    RemoveResult removeResult = memberService.remove(mid);

    JSONObject jsonObject = new JSONObject(); // {}

    if(removeResult == RemoveResult.SUCCESS) {
      jsonObject.put("result", "success");
    } else {
      jsonObject.put("result", "fail");
    }

    return jsonObject.toString(); // {"result": "success"}
  }


  @GetMapping("/temp")
  public String temp() {
    for(int i=1; i<=10000; i++) {
      Board board = new Board();
      board.setBtitle("제목" + i);
      board.setBcontent("내용" + i);
      board.setBwriter("user2");

      boardService.create(board);
    }
    return "1000개의 게시물 생성됨";
  }


  @GetMapping("/board-list")
  public List<Board> boardList(@RequestParam(value="pageNo", defaultValue = "1") int pageNo) {
    log.info("pageNo: " + pageNo);

    Pager pager = new Pager(10, 10, 10000, pageNo);

    List<Board> list = boardService.getListByPage(pager);

    return list;
  }

}
