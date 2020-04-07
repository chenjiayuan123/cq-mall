package com.banyuan.demo.token.service.impl;

import club.banyuan.demo.token.service.TokenService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","iat":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * <p>
 * payload-标准中注册的声明 (建议但不强制使用)
 * iss (issuer)：签发人
 * exp (expiration time)：过期时间
 * sub (subject)：主题
 * aud (audience)：受众
 * nbf (Not Before)：生效时间
 * iat (Issued At)：签发时间
 * jti (JWT ID)：编号jwt的唯一身份标识，主要用来作为一次性token
 * <p>
 * 定义位置
 * io.jsonwebtoken.Claims
 * 例如
 * io.jsonwebtoken.Claims.SUBJECT
 */
@Service
public class JwtTokenServiceImpl implements TokenService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

  @Value("${token.secret}")
  private String secret;
  @Value("${token.expireSec}")
  private Long expireSec;

  @Override
  public String generateToken(String subject) {
    if (StrUtil.isBlank(subject)) {
      throw new IllegalArgumentException("subject is blank");
    }
    return Jwts.builder()
        .setSubject(subject) // 用户名
        .setIssuedAt(new Date()) // token生成的时间
        .setExpiration(new Date(System.currentTimeMillis() + expireSec * 1000)) // token过期的时间
        .signWith(SignatureAlgorithm.HS512, secret) // 指定加密方式和秘钥
        .compact();
  }

  @Override
  public String generateToken(Map<String, Object> subjects) {
    if (CollectionUtil.isEmpty(subjects)) {
      throw new IllegalArgumentException("subjects is empty");
    }
    return Jwts.builder()
        .setClaims(subjects) // 可以指定多个自定义的键值对
        .setIssuedAt(new Date()) // token生成的时间
        .setExpiration(new Date(System.currentTimeMillis() + expireSec * 1000)) // token过期的时间
        .signWith(SignatureAlgorithm.HS512, secret) // 指定加密方式和秘钥
        .compact();
  }

  /**
   * 反序列化回来相当于把JSON String序列化回来，不能准确的转换为对应的对象类型
   *
   * @param token
   * @return 返回的map中包含{exp=1585999247, iat=1585996247}
   */
  @Override
  public Map<String, Object> parseMap(String token) {
    Claims claims = getTokenBody(token);
    return new HashMap<>(claims);
  }

  @Override
  public String parseSubject(String token) {
    return getTokenBody(token).getSubject();
  }

  @Override
  public String refreshExpireDate(String token) {
    if (StrUtil.isEmpty(token)) {
      throw new IllegalArgumentException("token illegal");
    }

    if (isExpired(token)) {
      throw new IllegalArgumentException("token expired");
    }

    Map<String, Object> body = parseMap(token);
    // 这里取出的map中包含token的创建时间和过期时间，不过不影响，在生成新token的时候时间会被覆盖掉
    return generateToken(body);
  }

  @Override
  public boolean isExpired(String token) {
    return getExpireSec(token) <= 0;
  }

  @Override
  public long getExpireSec(String token) {
    return (getTokenBody(token).getExpiration().getTime() - System.currentTimeMillis()) / 1000;
  }

  private Claims getTokenBody(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      // 如果token过期会抛出ExpiredJwtException，其中可以获取到claim
      LOGGER.warn("token过期,token={}", token);
      return e.getClaims();
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
      throw new IllegalArgumentException("无效的token", e);
    }
  }
}
