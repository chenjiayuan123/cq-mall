package club.banyuan.mall.mgt.config;

import club.banyuan.mall.mgt.security.DynamicResourceFilter;
import club.banyuan.mall.mgt.security.JwtAuthenticationFilter;
import club.banyuan.mall.mgt.security.RestAccessDeniedHandler;
import club.banyuan.mall.mgt.security.RestAuthenticationEntryPoint;
import club.banyuan.mall.mgt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * 用于手动注入一个new 出来对象的autowire属性
   */
  @Autowired
  private AutowireCapableBeanFactory beanFactory;

  @Autowired
  private AdminService adminService;

  @Override
  public void configure(WebSecurity web) throws Exception {
    // 这里配置的路径不走security的过滤器
    web.ignoring().antMatchers("/admin/login")
        // admin/info 会先发option请求，并且header里面没有token，因此这里放行option请求
        .antMatchers(HttpMethod.OPTIONS, "/**");
  }

  /**
   * security 两种认证
   * authentication 身份认证
   * authorization 权限认证
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() // 关闭默认的csrf
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 不使用session

    // 任何请求需要身份认证
    http.authorizeRequests().anyRequest().authenticated();

    // 自定义权限拒绝处理类
    // AuthenticationException指的是未登录状态下访问受保护资源
    // AccessDeniedException指的是登陆了但是由于权限不足(比如普通用户访问管理员界面）。
    http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .accessDeniedHandler(new RestAccessDeniedHandler());

    // 添加自定义的jwt过滤器
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
    // 注入属性
    beanFactory.autowireBean(jwtAuthenticationFilter);
    // 如果使用addFilter 则会抛异常，没有指定order
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    DynamicResourceFilter dynamicResourceFilter = new DynamicResourceFilter();
    beanFactory.autowireBean(dynamicResourceFilter);
    http.addFilterBefore(dynamicResourceFilter, FilterSecurityInterceptor.class);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> adminService.loadUserByUsername(username);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
