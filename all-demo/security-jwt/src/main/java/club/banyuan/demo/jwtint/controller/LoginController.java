package club.banyuan.demo.jwtint.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(@RequestParam String username,
      @RequestParam String password) {
    return "test";
  }

  @RequestMapping(value = "/auth1", method = RequestMethod.GET)
  @ResponseBody
  public String auth1() {
    return "auth1";
  }

  @RequestMapping(value = "/auth2", method = RequestMethod.GET)
  @ResponseBody
  public String auth2(@RequestParam String objectName) {
    return "auth2";
  }
}
