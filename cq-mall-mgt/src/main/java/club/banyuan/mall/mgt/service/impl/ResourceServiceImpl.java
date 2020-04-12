package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.dao.UmsResourceDao;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.service.ResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private UmsResourceDao umsResourceDao;

  @Override
  public List<UmsResource> listAllResource() {
    return umsResourceDao.selectAll();
  }

  @Override
  public List<UmsResource> listResourceByUsername(String name) {
    return umsResourceDao.selectByUsername(name);
  }
}
