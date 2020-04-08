package club.banyuan.demo.jwtint.config;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AutowireCapableBeanFactory beanFactory;


  @Autowired
  private UserDetailsService userDetailsService;

  // @Override
  // public void configure(WebSecurity web) throws Exception {
  //   web.ignoring().antMatchers("/admin/login");
  //   super.configure(web);
  // }

  /**
   * 这里配置的内容不走security的过滤器
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/admin/login", "/admin/auth1");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
        .authorizeRequests();
    registry.and()
        .csrf()
        .disable() // 关闭默认的csrf
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 不使用session
    // // //不需要保护的资源路径允许访问
    // // for (String url : ignoreUrlsConfig().getUrls()) {
    // //     registry.antMatchers(url).permitAll();
    // // }
    // // 放行登录接口
    // registry
    //     .antMatchers("/file/login").permitAll();
    // // .antMatchers("/admin/login").permitAll();

    //允许跨域请求的OPTIONS请求
    // 任何请求需要身份认证
    registry.and()
        .authorizeRequests()
        .anyRequest()
        .authenticated();

    // 自定义权限拒绝处理类
    // AuthenticationException指的是未登录状态下访问受保护资源
    // AccessDeniedException指的是登陆了但是由于权限不足(比如普通用户访问管理员界面）。
    registry
        .and()
        .exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler())
        .authenticationEntryPoint(restAuthenticationEntryPoint());
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();

    beanFactory.autowireBean(jwtAuthenticationTokenFilter);
    //     // 自定义权限拦截器JWT过滤器
    registry
        .and()
        .addFilterBefore(jwtAuthenticationTokenFilter,
            UsernamePasswordAuthenticationFilter.class);
  }

  // @Bean
  // public JwtAuthenticationTokenFilter getJwtAuthenticationTokenFilter() {
  //   return new JwtAuthenticationTokenFilter();
  // }

  @Bean
  public UserDetailsService userDetailsService() {
    //获取登录用户信息
    return username -> new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
      }

      @Override
      public String getPassword() {
        return "admin";
      }

      @Override
      public String getUsername() {
        return "zhangsan";
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }
    };
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  // @Bean
  // public PasswordEncoder passwordEncoder() {
  //   return new BCryptPasswordEncoder();
  // }

  // @Bean
  // public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
  //   return new JwtAuthenticationTokenFilter();
  // }

  // @Bean
  // @Override
  // public AuthenticationManager authenticationManagerBean() throws Exception {
  //     return super.authenticationManagerBean();
  // }
  //
  @Bean
  public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
    return new RestfulAccessDeniedHandler();
  }

  @Bean
  public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new RestAuthenticationEntryPoint();
  }
  //
  // @Bean
  // public IgnoreUrlsConfig ignoreUrlsConfig() {
  //     return new IgnoreUrlsConfig();
  // }
  //
  // @Bean
  // public JwtTokenUtil jwtTokenUtil() {
  //     return new JwtTokenUtil();
  // }
  //
  // @ConditionalOnBean(name = "dynamicSecurityService")
  // @Bean
  // public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
  //     return new DynamicAccessDecisionManager();
  // }
  //
  //
  // @ConditionalOnBean(name = "dynamicSecurityService")
  // @Bean
  // public DynamicSecurityFilter dynamicSecurityFilter() {
  //     return new DynamicSecurityFilter();
  // }
  //
  // @ConditionalOnBean(name = "dynamicSecurityService")
  // @Bean
  // public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
  //     return new DynamicSecurityMetadataSource();
  // }

}
