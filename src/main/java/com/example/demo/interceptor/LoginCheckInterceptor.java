package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component // 빈을 주입 시킬 수 있음
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Autowired
  private JwtService jwtService;

  // 전처리
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // log.info("전처리 실행");

    // 1) 요청 매핑 메소드에 @Login이 붙어 있는지 확인
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Login login = handlerMethod.getMethodAnnotation(Login.class);
    if (login != null) {
      // @Login 붙어 있을 경우
      // 2) JWT가 있는지 확인
      String authorization = request.getHeader("Authorization");
      if (authorization == null || authorization.equals("")) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT가 없습니다.");
        return false;
      } else {
        String jwt = authorization.substring(7);
        if (jwtService.validateJwt(jwt)) { // 유효할 경우
          return true;
        } else { // 유효하지 않을 경우
          response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT가 유효하지 않습니다.");
          return false;
        }
      }
    } else {
      // @Login이 붙어 있지 않을 경우
      return true;
    }

  }

  // 후처리
  // @Override
  // public void postHandle(HttpServletRequest request, HttpServletResponse
  // response, Object handler,
  // ModelAndView modelAndView) throws Exception {
  // log.info("후처리 실행");
  // }
}
