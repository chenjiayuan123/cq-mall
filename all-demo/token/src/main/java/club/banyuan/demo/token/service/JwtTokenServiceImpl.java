// package club.banyuan.demo.token.service;
//
// import cn.hutool.core.date.DateUtil;
// import cn.hutool.core.util.StrUtil;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
//
// /**
//  * JWT token的格式：header.payload.signature
//  * header的格式（算法、token的类型）：
//  * {"alg": "HS512","typ": "JWT"}
//  * payload的格式（用户名、创建时间、生成时间）：
//  * {"sub":"wang","iat":1489079981393,"exp":1489684781}
//  * signature的生成算法：
//  * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
//  * <p>
//  * payload-标准中注册的声明 (建议但不强制使用)
//  * iss: jwt签发者
//  * sub: jwt所面向的用户
//  * aud: 接收jwt的一方
//  * exp: jwt的过期时间，这个过期时间必须要大于签发时间
//  * nbf: 定义在什么时间之前，该jwt都是不可用的.
//  * iat: jwt的签发时间
//  * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
//  * <p>
//  * 定义位置
//  * io.jsonwebtoken.Claims
//  * 例如
//  * io.jsonwebtoken.Claims.SUBJECT
//  */
// public class JwtTokenServiceImpl {
//
//   private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenServiceImpl.class);
//   private static final String CLAIM_KEY_USERNAME = "sub";
//
//   @Value("${jwt.secret}")
//   private String secret;
//   @Value("${jwt.expiration}")
//   private Long expiration;
//   @Value("${jwt.tokenHead}")
//   private String tokenHead;
//
//   /**
//    * 根据负责生成JWT的token
//    */
//   private String generateToken(String sub) {
//     return Jwts.builder()
//         .setSubject(sub)
//         .setIssuedAt(new Date())
//         .setExpiration(generateExpirationDate())
//         .signWith(SignatureAlgorithm.HS512, secret)
//         .compact();
//   }
//
//   /**
//    * 从token中获取JWT中的负载
//    */
//   private Claims getClaimsFromToken(String token) {
//     Claims claims = null;
//     try {
//       claims = Jwts.parser()
//           .setSigningKey(secret)
//           .parseClaimsJws(token)
//           .getBody();
//     } catch (Exception e) {
//       LOGGER.info("JWT格式验证失败:{}", token);
//     }
//     return claims;
//   }
//
//   /**
//    * 生成token的过期时间
//    */
//   private Date generateExpirationDate() {
//     return new Date(System.currentTimeMillis() + expiration * 1000);
//   }
//
//   /**
//    * 从token中获取登录用户名
//    */
//   public String getUserNameFromToken(String token) {
//     String username;
//     try {
//       Claims claims = getClaimsFromToken(token);
//       //TODO??
//       username = claims.getSubject();
//     } catch (Exception e) {
//       username = null;
//     }
//     return username;
//   }
//
//   /**
//    * 验证token是否还有效
//    *
//    * @param token   客户端传入的token
//    * @param subject 用户信息
//    */
//   public boolean validateToken(String token, String subject) {
//     String username = getUserNameFromToken(token);
//     return username.equals(subject) && !isTokenExpired(token);
//   }
//
//   /**
//    * 判断token是否已经失效
//    */
//   private boolean isTokenExpired(String token) {
//     Date expiredDate = getExpiredDateFromToken(token);
//     return expiredDate.before(new Date());
//   }
//
//   /**
//    * 从token中获取过期时间
//    */
//   private Date getExpiredDateFromToken(String token) {
//     Claims claims = getClaimsFromToken(token);
//     return claims.getExpiration();
//   }
//
//   /**
//    * 当原来的token没过期时是可以刷新的
//    *
//    * @param oldToken 带tokenHead的token
//    */
//   public String refreshHeadToken(String oldToken) {
//     if (StrUtil.isEmpty(oldToken)) {
//       return null;
//     }
//     String token = oldToken.substring(tokenHead.length());
//     if (StrUtil.isEmpty(token)) {
//       return null;
//     }
//     //token校验不通过
//     Claims claims = getClaimsFromToken(token);
//     if (claims == null) {
//       return null;
//     }
//     //如果token已经过期，不支持刷新
//     if (isTokenExpired(token)) {
//       return null;
//     }
//     //如果token在30分钟之内刚刷新过，返回原token
//     if (tokenRefreshJustBefore(token, 30 * 60)) {
//       return token;
//     } else {
//       claims.put(Claims.ISSUED_AT, new Date());
//       return generateToken(claims);
//     }
//   }
//
//   /**
//    * 判断token在指定时间内是否刚刚刷新过
//    *
//    * @param token 原token
//    * @param time  指定时间（秒）
//    */
//   private boolean tokenRefreshJustBefore(String token, int time) {
//     Claims claims = getClaimsFromToken(token);
//     Date created = claims.get(Claims.ISSUED_AT, Date.class);
//     Date refreshDate = new Date();
//     //刷新时间在创建时间的指定时间内
//     return refreshDate.after(created) && refreshDate
//         .before(DateUtil.offsetSecond(created, time));
//   }
// }
