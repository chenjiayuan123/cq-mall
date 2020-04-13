package club.banyuan.mall.mgt.service.impl;

import club.banyuan.mall.mgt.common.ReqFailException;
import club.banyuan.mall.mgt.dao.UmsAdminDao;
import club.banyuan.mall.mgt.dao.UmsRoleDao;
import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.dao.entity.UmsResource;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import club.banyuan.mall.mgt.dto.AdminUserDetails;
import club.banyuan.mall.mgt.security.ResourceConfigAttribute;
import club.banyuan.mall.mgt.service.AdminService;
import club.banyuan.mall.mgt.service.ResourceService;
import club.banyuan.mall.mgt.service.TokenService;
import club.banyuan.mall.mgt.vo.AdminInfoVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private UmsAdminDao umsAdminDao;

  @Autowired
  private UmsRoleDao umsRoleDao;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public String login(String username, String password) {
    UserDetails userDetails = loadUserByUsername(username);
    if (passwordEncoder.matches(password, userDetails.getPassword())) {
      return tokenService.generateToken(username);
    }
    throw new RuntimeException("登录异常");
  }

  @Override
  public UmsAdmin getUserByUsername(String username) {
    return umsAdminDao.selectByUsername(username);
  }

  @Override
  public AdminUserDetails loadUserByUsername(String username) {
    UmsAdmin umsAdmin = umsAdminDao.selectByUsername(username);
    List<UmsResource> userResources = resourceService.listResourceByUsername(username);
    if (umsAdmin == null) {
      throw new ReqFailException("user not found");
    }
    List<ResourceConfigAttribute> configAttributes = userResources.stream()
        .map(ResourceConfigAttribute::new)
        .collect(
            Collectors.toList());
    return new AdminUserDetails(umsAdmin, configAttributes);
  }

  @Override
  public AdminInfoVo getUserInfo(String username) {
    AdminUserDetails adminUserDetails = loadUserByUsername(username);
    UmsAdmin umsAdmin = adminUserDetails.getAdmin();
    List<UmsRole> umsRoleList = umsRoleDao.selectByUserId(umsAdmin.getId());

    AdminInfoVo adminInfoVo = new AdminInfoVo();
    adminInfoVo.setIcon(umsAdmin.getIcon());
    adminInfoVo.setUsername(umsAdmin.getUsername());
    adminInfoVo.setRoles(umsRoleList.stream().map(UmsRole::getName).collect(Collectors.toList()));
    adminInfoVo.setMenus(resourceService.getMenusByRoleIds(umsRoleList.stream().map(UmsRole::getId).collect(Collectors.toList())));
    return adminInfoVo;
  }
}
