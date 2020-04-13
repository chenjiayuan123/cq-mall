package club.banyuan.mall.mgt.controller;

import club.banyuan.mall.mgt.common.CommonResult;
import club.banyuan.mall.mgt.dto.AdminLoginParam;
import club.banyuan.mall.mgt.service.AdminService;
import club.banyuan.mall.mgt.vo.LoginTokenRlt;
import java.security.Principal;
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


  @RequestMapping(value = "/info", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult info(Principal principal) {
    return CommonResult.success(adminService.getUserInfo(principal.getName()));
  }

  @RequestMapping(value = "/auth2", method = RequestMethod.GET)
  @ResponseBody
  public String auth2() {
    return "auth2";
  }
}
