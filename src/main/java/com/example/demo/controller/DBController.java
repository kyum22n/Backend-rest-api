package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.mybatis.MemberDao;
import com.example.demo.dto.Board;
import com.example.demo.dto.LoginForm;
import com.example.demo.dto.Member;
import com.example.demo.dto.Pager;
import com.example.demo.interceptor.Login;
import com.example.demo.service.BoardService;
import com.example.demo.service.JwtService;
import com.example.demo.service.MemberService;
import com.example.demo.service.MemberService.RemoveResult;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

  @Autowired
  private JwtService jwtService;

  // 회원 가입
  @PostMapping("/member-create")
  public Map<String, Object> memberCreate(@RequestBody Member member) {
    log.info(member.toString());
    // 1)유효성 검사
    
    // 2)회원 가입 처리
    Map<String, Object> map = new HashMap<>();
    // 암호화 알고리즘
    try {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodedPassword = passwordEncoder.encode(member.getMpassword());
      member.setMpassword(encodedPassword);
      
      memberService.join(member);

      // 3)응답 생성
      map.put("result", "success");
      
    } catch(Exception e) {
      map.put("result", "fail");
      map.put("message", e.getMessage());
    }

    return map;
  }
  
  // 회원 로그인하기
  @PostMapping("/member-login")
  public Map<String, Object> memberLogin(@RequestBody LoginForm loginForm) {

    Map<String, Object> map = new HashMap<>();


    // DB에 있는 member 불러오기
    Member member = memberService.getMember(loginForm.getMid());
    if(member == null) {
      map.put("result", "fail");
      map.put("message", "아이디가 없음");
    } else {
      // 암호화 알고리즘
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
      // 암호화된 비밀번호 값 비교하기
      boolean result = passwordEncoder.matches(loginForm.getMpassword(), member.getMpassword());
      if(result) {
        String jwt = jwtService.createJwt(member.getMid(), member.getMemail());
        map.put("result", "success");
        map.put("mid", member.getMid());
        map.put("jwt", jwt);
      } else {
        map.put("result", "fail");
        map.put("message", "비밀번호가 틀림");
      }
    }

    return map;
        
  }
    
  // 회원 정보 가져오기
  @Login
  @GetMapping("/member-info")
  public Map<String, Object> memberInfo(
    @RequestParam("mid") String mid,
    @RequestHeader("Authorization") String authorization) {

    Map<String, Object> resultMap = new HashMap<>();
    Member member = memberService.getMember(mid);
    resultMap.put("result", "success");
    resultMap.put("data", member);

    return resultMap;
  }
    
  // 회원 정보 수정하기
  @Login
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
  @Login
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
  public Map<String, Object> boardList(@RequestParam(value="pageNo", defaultValue = "1") int pageNo) {
    log.info("pageNo: " + pageNo);

    int totalRows = boardService.getTotalRows();
    Pager pager = new Pager(10, 10, totalRows, pageNo);

    List<Board> list = boardService.getListByPage(pager);

    Map<String, Object> map = new HashMap<>();
    map.put("pager", pager);
    map.put("boards", list);

    return map;
  }

  @PostMapping("/board-create")
  @Login
  public Board boardCreate(Board board) throws IOException {
    log.info(board.toString());
    
    MultipartFile mf = board.getBattach();
    if(mf != null && !mf.isEmpty()) {
      board.setBattachoname(mf.getOriginalFilename());  // 사용자가 올린 파일 이름 저장
      board.setBattachtype(mf.getContentType());  // 사용자가 올린 파일 타입 저장
      board.setBattachdata(mf.getBytes());  // 사용자가 올린 파일의 바이트 데이터 얻기
    }

    boardService.create(board);

    Board dbBoard = boardService.getBoard(board.getBno());

    return dbBoard;
  }
  
  @Login
  @GetMapping("/board-detail")
  public Board boardDetail(@RequestParam("bno") int bno) {
    Board board = boardService.getBoard(bno);
    return board;
  }
  
  @Login
  @GetMapping("/board-attach-download")
  public void boardAttachDownload(@RequestParam("bno") int bno, HttpServletResponse response) throws Exception {
    
    Board board = boardService.getBoard(bno);

    String fileName = board.getBattachoname();

    //응답 헤더에 Content-Type 추가
    response.setContentType(board.getBattachtype());
    //response.setHeader("Content-Type", board.getBattachtype());
    
    //본문의 내용을 파일로 저장할 수 있도록 헤더 추가
    String encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
    
    //응답 본문으로 데이터를 출력하는 스트림
    OutputStream os = response.getOutputStream();
    // 성능 향상
    BufferedOutputStream bos = new BufferedOutputStream(os);
    
    // 응답 본문에 파일 데이터 출력
    bos.write(board.getBattachdata());
    
    bos.flush();
    bos.close();
  }
  
  @Login
  @PutMapping("/board-update")
  public Board boardUpdate(Board board) throws IOException {
    log.info(board.toString());
    
    MultipartFile mf = board.getBattach();
    if(mf != null && !mf.isEmpty()) {
      board.setBattachoname(mf.getOriginalFilename());  // 사용자가 올린 파일 이름 저장
      board.setBattachtype(mf.getContentType());  // 사용자가 올린 파일 타입 저장
      board.setBattachdata(mf.getBytes());  // 사용자가 올린 파일의 바이트 데이터 얻기
    }

    boardService.modify(board);

    Board dbBoard = boardService.getBoard(board.getBno());

    return dbBoard;
  }

  @Login
  @DeleteMapping("/board-delete")
  public String boardDelete(@RequestParam("bno") int bno) {
    boardService.remove(bno);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", "success");
    return jsonObject.toString(); // {"result": "success"}
  }
  

}
