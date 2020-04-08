package club.banyuan.demo.jwtint.security;

import club.banyuan.demo.jwtint.common.CommonResult;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 统一包装用户认证失败的返回信息
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().println(JSONUtil.parse(new CommonResult("用户未登陆或登陆过期")));
  }
}
