package club.banyuan.mall.mgt.service.impl;


import static org.junit.Assert.*;

import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.service.AdminService;
import club.banyuan.mall.mgt.vo.AdminInfoVo;
import cn.hutool.core.collection.CollectionUtil;
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

  @Test
  public void getUserInfo() {
    AdminInfoVo adminInfoVo = adminService.getUserInfo("admin");
    assertTrue(CollectionUtil.isNotEmpty(adminInfoVo.getMenus()));
    assertTrue(CollectionUtil.isNotEmpty(adminInfoVo.getRoles()));
    assertEquals("admin", adminInfoVo.getUsername());
  }
}