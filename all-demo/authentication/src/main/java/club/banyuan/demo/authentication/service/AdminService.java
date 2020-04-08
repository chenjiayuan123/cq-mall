package club.banyuan.demo.authentication.service;

import club.banyuan.demo.authentication.dao.entity.UmsAdmin;
import club.banyuan.demo.authentication.security.AdminUserDetails;

public interface AdminService {

  String login(String username, String password);

  UmsAdmin getUserByUsername(String username);

  AdminUserDetails loadUserByUsername(String username);
}
