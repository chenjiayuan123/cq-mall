package club.banyuan.demo.authentication.security;

import cn.hutool.core.collection.CollectionUtil;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    if (CollectionUtil.isEmpty(configAttributes)) {
      return;
    }

    Set<String> userAllAuth = authentication.getAuthorities().stream().map(
        GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    Set<String> resourceAuth = configAttributes.stream().map(ConfigAttribute::getAttribute)
        .collect(Collectors.toSet());

    if (!userAllAuth.containsAll(resourceAuth)) {
      throw new AccessDeniedException("没有访问权限");
    }
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
