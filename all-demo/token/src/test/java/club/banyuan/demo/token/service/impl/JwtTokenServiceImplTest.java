package club.banyuan.demo.token.service.impl;

import static org.junit.Assert.*;

import club.banyuan.demo.token.service.TokenService;
import club.banyuan.demo.token.service.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenServiceImplTest {

  @Autowired()
  private TokenService tokenService;

  @Test
  public void generateToken() {
    // 重设过期时间
    ReflectionTestUtils.setField(tokenService, "expireSec", 3000L);
    String subject = "zhangsan";
    String token = tokenService.generateToken(subject);
    System.out.println(token);
    assertTrue(3000L - tokenService.getExpireSec(token) < 3);
    assertEquals(subject, tokenService.parseSubject(token));
    assertFalse(tokenService.isExpired(token));
  }

  @Test
  public void testRefreshExpireDate() throws InterruptedException {
    long tokenExpireSec = 3000L;
    // 重设过期时间
    ReflectionTestUtils.setField(tokenService, "expireSec", tokenExpireSec);
    String subject = "zhangsan";
    String token = tokenService.generateToken(subject);
    assertTrue(tokenExpireSec - tokenService.getExpireSec(token) < 3);
    Thread.sleep(3000);
    assertFalse(tokenExpireSec - tokenService.getExpireSec(token) < 3);
    String newToken = tokenService.refreshExpireDate(token);
    assertTrue(tokenExpireSec - tokenService.getExpireSec(newToken) < 3);
    assertEquals(subject, tokenService.parseSubject(newToken));
    assertFalse(tokenService.isExpired(newToken));
    assertEquals(tokenService.parseSubject(token), tokenService.parseSubject(newToken));
  }

  @Test
  public void testExpired() throws InterruptedException {
    // 重设过期时间
    ReflectionTestUtils.setField(tokenService, "expireSec", 1L);
    String subject = "zhangsan";
    String token = tokenService.generateToken(subject);
    assertTrue(tokenService.isExpired(token));
    Thread.sleep(2000);
    assertTrue(tokenService.getExpireSec(token) < 0);
  }

  @Test
  public void testParseMap() {
    ReflectionTestUtils.setField(tokenService, "expireSec", 3000L);
    Map<String, Object> map = new HashMap<>();
    map.put("user", new User("zhangsan", "123456"));
    String token = tokenService.generateToken(map);
    Map<String, Object> resultMap = tokenService.parseMap(token);
    Object userObj = resultMap.get("user");
    assertNotNull(userObj);
    assertFalse(userObj instanceof User);
  }

  /**
   * 对于jwt.io 中提供的调试工具，选中secret base64 encoded，并且需要填入使用如下转换的秘钥
   */
  @Test
  public void parse(){
    // 需要将秘钥进行如下转换之后填写到jwt.io 中
    System.out.println(
    javax.xml.bind.DatatypeConverter.printBase64Binary(
    javax.xml.bind.DatatypeConverter.parseBase64Binary("banyuan")));

    System.out.println(tokenService.generateToken("admin"));


    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwiaWF0IjoxNTg2MTc5OTE5LCJleHAiOjE1ODYxODM1MTl9.bgdX-KRBrqwPvXW4WSL_L4DcJN_Z3fucIWZ8_7hcADz1jsELb13XWnuz6qces7NNzFJ9m71ZndOWkMt6IXx-UQ";
    System.out.println(tokenService.parseSubject(token));
  }

}