package club.banyuan.demo.authentication.service.impl;

import club.banyuan.demo.authentication.service.TokenService;
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

  @Value("${token.expireSec}")
  private long expireSec;

  @Value("${token.secretKey}")
  private String secretKey;

  @Override
  public String generateToken(String subject) {
    if (StrUtil.isBlank(subject)) {
      throw new IllegalArgumentException("subject is blank");
    }

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expireSec * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  @Override
  public String generateToken(Map<String, ?> subjects) {
    if (CollectionUtil.isEmpty(subjects)) {
      throw new IllegalArgumentException("subjects is empty");
    }

    return Jwts.builder()
        .setClaims(new HashMap<>(subjects))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expireSec * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  @Override
  public String parseSubject(String token) {
    Claims claims = getBody(token);
    return claims.getSubject();
  }

  /**
   * 反序列化回来相当于把JSON String序列化回来，不能准确的转换为对应的对象类型
   *
   * @param token
   * @return 返回的map中包含{exp=1585999247, iat=1585996247}
   */
  @Override
  public Map<String, Object> parseMap(String token) {
    Claims claims = getBody(token);
    return new HashMap<>(claims);
  }

  @Override
  public String refreshExpireDate(String token) {
    if (StrUtil.isBlank(token)) {
      throw new IllegalArgumentException("token is blank");
    }
    if (isExpired(token)) {
      throw new IllegalArgumentException("token is expire");
    }
    // 这里取出的map中包含token的创建时间和过期时间，不过不影响，在生成新token的时候时间会被覆盖掉
    return generateToken(parseMap(token));
  }

  @Override
  public boolean isExpired(String token) {
    return getExpireSec(token) <= 0;
  }

  @Override
  public long getExpireSec(String token) {
    return (getBody(token).getExpiration().getTime() - System.currentTimeMillis()) / 1000;
  }

  private Claims getBody(String token) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      // 如果token过期会抛出ExpiredJwtException，其中可以获取到claim
      return e.getClaims();
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
      throw new IllegalArgumentException("无效的token", e);
    }
    return claims;
  }


}
