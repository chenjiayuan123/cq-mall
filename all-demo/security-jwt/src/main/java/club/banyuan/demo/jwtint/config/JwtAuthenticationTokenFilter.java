package club.banyuan.demo.jwtint.config;

import club.banyuan.demo.jwtint.service.TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT登录授权过滤器
 * spring会自动注册filter，所以手动添加security的filter的时候，这个类需要手动new
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

  @Value("${token.header}")
  private String authHeader;

  @Value("${token.expireSec}")
  private long expireSec;

  @Value("${token.schema}")
  private String schema;

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private TokenService tokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(this.authHeader);
    if (authHeader != null && authHeader.startsWith(this.schema)) {
      String authToken = authHeader.substring(this.schema.length());// The part after "Bearer "
      String username = tokenService.parseSubject(authToken);
      LOGGER.info("checking username:{}", username);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (userDetails != null && username.equals(userDetails.getUsername())) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          LOGGER.info("authenticated user:{}", username);
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }
}


