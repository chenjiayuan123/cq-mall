package club.banyuan.demo.authentication.security;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import club.banyuan.demo.authentication.service.ResourceService;
import cn.hutool.core.util.URLUtil;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class DynamicMetadataSource implements FilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;

  /**
   * 返回访问路径所需要的资源列表(字符串)
   *
   * @param object
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

    List<UmsResource> resourceList = resourceService.listAllResource();

    FilterInvocation filterInvocation = (FilterInvocation) object;
    String url = filterInvocation.getRequestUrl();
    String path = URLUtil.getPath(url);
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    return resourceList.stream()
        .filter(t -> antPathMatcher.match(t.getUrl(), path))
        .map(ResourceConfigAttribute::new)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
