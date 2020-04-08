package club.banyuan.demo.authentication.service;

import java.util.Map;

public interface TokenService {

  String generateToken(String subject);

  String generateToken(Map<String, ?> subjects);

  String parseSubject(String token);

  Map<String, Object> parseMap(String token);

  String refreshExpireDate(String token);

  boolean isExpired(String token);

  long getExpireSec(String token);
}
