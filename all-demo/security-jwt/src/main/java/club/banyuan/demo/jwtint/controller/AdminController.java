package club.banyuan.demo.jwtint.controller;

import club.banyuan.demo.jwtint.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(@RequestParam String username,
      @RequestParam String password) {
    return adminService.login(username, password);
  }

  @RequestMapping(value = "/auth1", method = RequestMethod.GET)
  @ResponseBody
  public String auth1() {
    return "auth1";
  }

  @RequestMapping(value = "/auth2", method = RequestMethod.GET)
  @ResponseBody
  public String auth2() {
    return "auth2";
  }
}
