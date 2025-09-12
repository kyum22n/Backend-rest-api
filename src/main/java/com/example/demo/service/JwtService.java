package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
  // 문자열 비밀키(노출이 되면 안 됨)
  private String strKey = "com.mycompony.backendapi.secret.key";
  // 서명 및 암호화를 위한 비밀키(SecretKey)
  private SecretKey secretKey;
  // JWT의 유효 기간(단위: 밀리세컨)
  private long jwtDuration = 24*60*60*1000;  // 하루

  // 생성자
  public JwtService() throws Exception {
    byte[] bytes = strKey.getBytes("UTF-8");
    secretKey = Keys.hmacShaKeyFor(bytes);
  }

  // JWT 생성 메소드
  public String createJwt(String mid, String memail) {
    // JWT를 생성하는 빌더 얻기
    JwtBuilder jwtBuilder = Jwts.builder();

    // JWT에 포함할 Payload 추가
    jwtBuilder.subject(mid);
    jwtBuilder.claim("memail", memail); // payload

    // JWT 유효기간 설정
    jwtBuilder.expiration(new Date(new Date().getTime() + jwtDuration));

    // SecretKey 설정
    jwtBuilder.signWith(secretKey);

    // JWT 얻기
    String jwt = jwtBuilder.compact();

    return jwt;
  }

  // JWT가 유효한지 검사
  public boolean validateJwt(String jwt) {
    boolean result = false;
    try {
      // JWT를 해석하는 JwtParser 얻기
      JwtParserBuilder jwtParserBuilder = Jwts.parser();
      jwtParserBuilder.verifyWith(secretKey);
      JwtParser jwtParser = jwtParserBuilder.build();
      
      // JWT 해석
      Jws<Claims> jws = jwtParser.parseSignedClaims(jwt);
      result = true;
    } catch(ExpiredJwtException e) {
      log.info("기간이 만료된 토큰입니다.");
    } catch(io.jsonwebtoken.security.SecurityException e) {
      log.info("잘못 서명된 토큰입니다.");     
    } catch(Exception e) {
      log.info("토큰을 해석할 수 없습니다.");     
    }

    return result;
  }
  
  // JWT 안의 정보(claims) 빼내기
  public Map<String, String> getClaims(String jwt) {
    // JWT를 해석하는 JwtParser 얻기
    JwtParserBuilder jwtParserBuilder = Jwts.parser();
    jwtParserBuilder.verifyWith(secretKey);
    JwtParser jwtParser = jwtParserBuilder.build();
    
    // JWT 해석
    Jws<Claims> jws = jwtParser.parseSignedClaims(jwt);
    Claims claims = jws.getPayload();

    Map<String, String> map = new HashMap<>();
    map.put("mid", claims.getSubject());
    map.put("memail", claims.get("memail").toString());

    return map;
  }
}
