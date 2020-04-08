package club.banyuan.demo.jwtint.service.impl;

import club.banyuan.demo.jwtint.service.AdminService;
import club.banyuan.demo.jwtint.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private TokenService tokenService;

  @Override
  public String login(String username, String password) {
    return tokenService.generateToken(username);
  }
}
