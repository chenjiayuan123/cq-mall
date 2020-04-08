package club.banyuan.demo.jwtint.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

  @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
  @ResponseBody
  public String upload(@RequestParam("file") MultipartFile file) {

    return "fail";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(@RequestParam String username,
      @RequestParam String password) {
    return "test";
  }

  @RequestMapping(value = "/image/delete", method = RequestMethod.POST)
  @ResponseBody
  public String delete(@RequestParam String objectName) {

    return "fail";
  }
}
