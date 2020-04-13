package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.dao.UmsMenuDao;
import club.banyuan.mall.mgt.dao.UmsResourceDao;
import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.service.ResourceService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private UmsResourceDao umsResourceDao;

  @Autowired
  private UmsMenuDao umsMenuDao;

  @Override
  public List<UmsResource> listAllResource() {
    return umsResourceDao.selectAll();
  }

  @Override
  public List<UmsMenu> listAllMenus() {
    return umsMenuDao.selectAll();
  }

  @Override
  public List<UmsResource> listResourceByUsername(String name) {
    return umsResourceDao.selectByUsername(name);
  }

  @Override
  public List<UmsMenu> getMenusByRoleIds(List<Long> roleIds) {
    List<UmsMenu> umsMenus = listAllMenus();
    List<Long> menuIds = umsMenuDao.selectMenuIdListByRoleIds(roleIds);
    return umsMenus.stream().filter(t -> menuIds.contains(t.getId())).collect(Collectors.toList());
  }
}
