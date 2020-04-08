package club.banyuan.demo.authentication.service;

import java.util.List;

public interface ResourceService {

  List<?> listAllResource();

  List<?> listResourceByUserName(String name);
}
