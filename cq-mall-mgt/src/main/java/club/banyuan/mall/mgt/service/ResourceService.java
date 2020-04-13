package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsMenu;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import java.util.List;

public interface ResourceService {

  List<UmsResource> listAllResource();

  List<UmsMenu> listAllMenus();

  List<UmsResource> listResourceByUsername(String username);

  List<UmsMenu> getMenusByRoleIds(List<Long> collect);
}
