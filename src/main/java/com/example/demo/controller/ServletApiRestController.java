package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/servlet-api-rest")
@Slf4j
public class ServletApiRestController {
  @GetMapping("/get-client-ip")
  public String getClientIp(HttpServletRequest request) {
    String clientIp = request.getRemoteAddr();
    //String userAgent = request.getHeader("User-Agent");
    //Cookie[] cookies = request.getCookies();
    return clientIp;
  }

  @GetMapping("/file-download")
  public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String fileName = "사진1.jpg";

    //응답 헤더에 Content-Type 추가
    response.setContentType("image/jpeg");
    //response.setHeader("Content-Type", "image/jpeg");

    //본문의 내용을 파일로 저장할 수 있도록 헤더 추가
    String encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

    //응답 본문으로 데이터를 출력하는 스트림
    OutputStream os = response.getOutputStream();

    //파일로부터 데이터를 입력하는 스트림
    InputStream is = new FileInputStream("C:/Temp/" + fileName);

    //방법1: 읽은 데이터를 바로 출력
    // byte[] arr = new byte[1024];
    // while(true) {
    //   int readNum = is.read(arr);
    //   if(readNum == -1) break;
    //   os.write(arr, 0, readNum);
    // }
    // os.flush();

    //방법2
    FileCopyUtils.copy(is, os);

    is.close();
    os.close();
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginForm loginForm, HttpSession session) {
      String mid = loginForm.getMid();
      String mpassword = loginForm.getMpassword();

      if(mid.equals("admin")) {
        if(mpassword.equals("kosa12345")) {
          //세션에 데이터(객체)를 저장
          session.setAttribute("login", mid);
          return "로그인 성공";
        }
      }
      
      return "로그인 실패";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    //세션에서 데이터 가져오기
    String mid = (String) session.getAttribute("login");
    if(mid != null) {
      //세션에서 데이터를 삭제
      session.removeAttribute("login");
      return "로그아웃 성공";
    } else {
      return "";
    }
  }

  @GetMapping("/login-check")
  public String loginCheck(HttpSession session) {
    //세션에서 데이터 가져오기
    String mid = (String) session.getAttribute("login");
    if(mid != null) {
      return mid + "로 로그인되어 있는 상태입니다.";
    } else {
      return "로그인이 되어 있지 않습니다.";
    }
  }
}
