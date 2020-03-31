package club.banyuan.demo.redis.service.impl;

import club.banyuan.demo.redis.service.CacheService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheServiceImplTest {

  @Autowired
  private CacheService cacheService;

  @Test
  public void set() {
    cacheService.set("test", "test");
    String str = cacheService.get("test");
    Assert.assertEquals("test", str);
  }

  @Test
  public void expire() {
    String key = "test";
    String value = "testval";
    cacheService.set(key, value);
    cacheService.expire(key, 3);
    System.out.println(cacheService.getExpire(key));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String val = cacheService.get(key);
    Assert.assertEquals(value, val);
    System.out.println(cacheService.getExpire(key));
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    val = cacheService.get(key);
    Assert.assertNull(val);

  }

}