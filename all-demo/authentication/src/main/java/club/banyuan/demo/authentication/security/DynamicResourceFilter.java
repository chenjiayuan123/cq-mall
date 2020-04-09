package club.banyuan.demo.authentication.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

public class DynamicResourceFilter extends AbstractSecurityInterceptor {

  @Override
  public Class<?> getSecureObjectClass() {
    return null;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return null;
  }
}
