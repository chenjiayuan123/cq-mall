package club.banyuan.demo.authentication.service.impl;

import club.banyuan.demo.authentication.dao.UmsAdminDao;
import club.banyuan.demo.authentication.dao.entity.UmsAdmin;
import club.banyuan.demo.authentication.security.AdminUserDetails;
import club.banyuan.demo.authentication.service.AdminService;
import club.banyuan.demo.authentication.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private UmsAdminDao umsAdminDao;

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
    if (umsAdmin == null) {
      // TODO
      throw new RuntimeException("user not found");
    }
    return new AdminUserDetails(umsAdmin);
  }
}
