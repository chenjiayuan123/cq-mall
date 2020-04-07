package com.banyuan.demo.token.service;

import java.util.Map;

public interface TokenService {

  String generateToken(String subject);

  String generateToken(Map<String, Object> subjects);

  Map<String, Object> parseMap(String token);

  String parseSubject(String token);

  String refreshExpireDate(String token);

  boolean isExpired(String token);

  long getExpireSec(String token);
}
