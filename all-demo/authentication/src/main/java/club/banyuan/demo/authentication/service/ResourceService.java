package club.banyuan.demo.authentication.service;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import java.util.List;

public interface ResourceService {

  List<UmsResource> listAllResource();

  List<UmsResource> listResourceByUsername(String username);
}
