package club.banyuan.demo.authentication.security;

import club.banyuan.demo.authentication.common.CommonResult;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 统一包装用户认证失败的返回信息
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().println(JSONUtil.parse(CommonResult.forbidden()));
  }
}
