package club.banyuan.mall.mgt.security;

import club.banyuan.mall.mgt.service.TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Value("${token.header}")
  private String authKey;

  @Value("${token.schema}")
  private String tokenSchema;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    String authHead = httpServletRequest.getHeader(authKey);
    if (authHead != null && authHead.startsWith(tokenSchema)) {
      String token = authHead.substring(tokenSchema.length());
      String subject = tokenService.parseSubject(token);

      UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

      // token校验通过，设置身份认证信息
      // 两个参数构造方法表示身份未认证，三个参数构造方法表示身份已认证
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          subject, userDetails.getPassword(), userDetails.getAuthorities());
      authenticationToken.setDetails(userDetails);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    // 无论身份是否通过都需继续要走过滤器
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
