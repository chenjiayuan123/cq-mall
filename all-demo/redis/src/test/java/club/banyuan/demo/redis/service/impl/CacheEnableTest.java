package club.banyuan.demo.redis.service.impl;

import club.banyuan.demo.redis.user.User;
import club.banyuan.demo.redis.user.UserService;
import club.banyuan.demo.redis.service.CacheService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheEnableTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CacheService cacheService;

  @Before
  public void beforeTest() {
    cacheService.flushdb();
  }

  @Test
  public void ableTest() {
    User one = userService.getUser();
    User two = userService.getUser();
    Assert.assertEquals(one, two);
  }

  @Test
  public void cacheEvictTest() {
    User one = userService.getUser();
    User two = userService.getUser();
    Assert.assertEquals(one, two);
    userService.evictUser();
    User three = userService.getUser();
    Assert.assertNotEquals(two, three);
  }

  @Test
  public void cachePut() {
    User one = userService.cachePut();
    User two = cacheService.get("users::cachePut");
    Assert.assertEquals(one, two);
  }
}
