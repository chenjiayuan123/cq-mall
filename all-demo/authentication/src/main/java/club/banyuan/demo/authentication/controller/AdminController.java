package club.banyuan.demo.authentication.controller;

import club.banyuan.demo.authentication.common.CommonResult;
import club.banyuan.demo.authentication.dto.AdminLoginParam;
import club.banyuan.demo.authentication.service.AdminService;
import club.banyuan.demo.authentication.vo.LoginTokenRlt;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

  @Value("${token.schema}")
  private String schema;

  @Autowired
  private AdminService adminService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult login(@RequestBody @Valid AdminLoginParam adminLoginParam) {
    String token = adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
    LoginTokenRlt rlt = new LoginTokenRlt(schema, token);
    return CommonResult.success(rlt);
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
