package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.security.AdminUserDetails;

public interface AdminService {

  String login(String username, String password);

  UmsAdmin getUserByUsername(String username);

  AdminUserDetails loadUserByUsername(String username);
}
