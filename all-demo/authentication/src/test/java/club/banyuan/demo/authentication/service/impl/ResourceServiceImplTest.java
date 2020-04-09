package club.banyuan.demo.authentication.service.impl;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import club.banyuan.demo.authentication.service.ResourceService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceImplTest {

  @Autowired
  private ResourceService resourceService;

  @Test
  public void selectAllTest() {
    List<UmsResource> umsResources = resourceService.listAllResource();
    Assert.assertTrue(CollectionUtil.isNotEmpty(umsResources));
    System.out.println(JSONUtil.toJsonPrettyStr(umsResources));
  }

  @Test
  public void selectByUsernameTest() {
    List<UmsResource> umsResources = resourceService.listResourceByUsername("admin");
    Assert.assertNotNull(umsResources);
    Assert.assertTrue(CollectionUtil.isNotEmpty(umsResources));
  }
}
