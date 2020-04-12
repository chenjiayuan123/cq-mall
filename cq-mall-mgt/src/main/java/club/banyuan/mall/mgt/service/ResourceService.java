package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsResource;
import java.util.List;

public interface ResourceService {

  List<UmsResource> listAllResource();

  List<UmsResource> listResourceByUsername(String username);
}
