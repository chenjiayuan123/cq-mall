package club.banyuan.demo.authentication.service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import club.banyuan.demo.authentication.dao.entity.UmsAdmin;
import club.banyuan.demo.authentication.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {

  @Autowired
  private AdminService adminService;

  @Test
  public void getUserByUsername() {
    UmsAdmin umsAdmin = adminService.getUserByUsername("admin");
    assertNotNull(umsAdmin);
    assertEquals("admin", umsAdmin.getUsername());
    assertEquals(3L, umsAdmin.getId().longValue());
  }
}