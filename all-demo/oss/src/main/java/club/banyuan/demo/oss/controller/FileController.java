package club.banyuan.demo.oss.controller;

import club.banyuan.demo.oss.service.OssFileService;
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

  @Autowired
  private OssFileService ossFileService;

  @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
  @ResponseBody
  public String upload(@RequestParam("file") MultipartFile file) {
    try {
      String filename = file.getOriginalFilename();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      // 设置存储对象名称
      String objectName = sdf.format(new Date()) + "/" + filename;
      return ossFileService.save(objectName, file.getInputStream(), file.getContentType());
    } catch (Exception e) {
      LOGGER.info("上传发生错误: {}！", e.getMessage());
    }
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
    try {
      ossFileService.delete(objectName);
      return "success";
    } catch (Exception e) {
      LOGGER.error("删除失败", e);
    }
    return "fail";
  }
}
