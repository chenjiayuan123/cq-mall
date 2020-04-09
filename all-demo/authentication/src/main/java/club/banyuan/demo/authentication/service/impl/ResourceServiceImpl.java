package club.banyuan.demo.authentication.service.impl;

import club.banyuan.demo.authentication.dao.UmsResourceDao;
import club.banyuan.demo.authentication.dao.entity.UmsResource;
import club.banyuan.demo.authentication.service.ResourceService;
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
