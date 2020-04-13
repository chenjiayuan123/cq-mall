package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.dto.AdminUserDetails;
import club.banyuan.mall.mgt.vo.AdminInfoVo;

public interface AdminService {

  String login(String username, String password);

  UmsAdmin getUserByUsername(String username);

  AdminUserDetails loadUserByUsername(String username);

  AdminInfoVo getUserInfo(String username);
}
