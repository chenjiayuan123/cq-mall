package club.banyuan.demo.jwt.service.impl;


import static org.junit.Assert.*;

import club.banyuan.demo.jwt.service.TokenService;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenServiceImplTest {

  @Autowired
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
  public void parseMap() {
    Map<String, String> map = new HashMap<>();
    map.put("userId", "1");
    map.put("userName", "zhangsan");

    String token = tokenService.generateToken(map);

    Map<String, Object> rlt = tokenService.parseMap(token);

    for (Entry<String, String> entry : map.entrySet()) {
      assertTrue(rlt.containsKey(entry.getKey()));
      assertEquals(entry.getValue(), rlt.get(entry.getKey()));
    }
  }

  @Test
  public void parseToken() {
    // String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4NjIyNzU0MSwiZXhwIjoxNTg2MjI3NTQxfQ.4NCVhg0LoWvNvi_rNNkOcdwSdoR2moUtNHWGx3T9wfDEFN2FWQWKRxeIVzUzmaccgJ3Rh3SYEcVZOOHyRmwIrA";
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOi12JhZG1pbiIsImlhdCI6MTU4NjIyNzc4MSwiZXhwIjoxNTg2MjMxMzgxfQ.hycu4AXNwIYVGva30LOp4KPVe4zihM-f7qAlBF5EOpjN7jjf3dTFIGolIYMT4zsMWzRe5_mQRre1QqZQEz3HLA";
    System.out.println(tokenService.parseSubject(token));
  }

  @Test
  public void testExpired() {
    ReflectionTestUtils.setField(tokenService, "expireSec", 1);
    String subject = "zhangsan";
    String token = tokenService.generateToken(subject);
    assertTrue(tokenService.isExpired(token));
    assertTrue(tokenService.getExpireSec(token) <= 0);
  }

  @Test
  public void testRefreshExpireDate() throws InterruptedException {
    long tokenExpireSec = 3000L;
    ReflectionTestUtils.setField(tokenService, "expireSec", tokenExpireSec);

    String subject = "zhangsan";
    String token = tokenService.generateToken(subject);
    System.out.println(tokenExpireSec - tokenService.getExpireSec(token));
    assertTrue(tokenExpireSec - tokenService.getExpireSec(token) < 3);
    Thread.sleep(3000);
    assertFalse(tokenExpireSec - tokenService.getExpireSec(token) < 3);
    String newToken = tokenService.refreshExpireDate(token);
    assertTrue(tokenExpireSec - tokenService.getExpireSec(newToken) < 3);
    assertEquals(subject, tokenService.parseSubject(newToken));

  }

  @Test
  public void encodeSecret() {
    System.out.println(
        javax.xml.bind.DatatypeConverter.printBase64Binary(
            javax.xml.bind.DatatypeConverter.parseBase64Binary("banyuan")));
  }
}