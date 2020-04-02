package club.banyuan.demo.redis.user;

import static org.junit.Assert.*;

import club.banyuan.demo.redis.service.CacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CacheService cacheService;


  @Before
  public void clearCache() {
    cacheService.flushdb();
  }

  @Test
  public void getUser() {
    assertEquals("0", userService.getUser().getPwd());
    assertEquals("0", userService.getUser().getPwd());
  }
}
