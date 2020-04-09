package club.banyuan.demo.authentication.security;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

public class ResourceConfigAttribute implements ConfigAttribute, GrantedAuthority {

  private String attribute;

  public ResourceConfigAttribute(String attribute) {
    this.attribute = attribute;
  }

  public ResourceConfigAttribute(UmsResource resource) {
    this.attribute = resource.getId() + ":" + resource.getName();
  }

  @Override
  public String getAttribute() {
    return attribute;
  }

  @Override
  public String getAuthority() {
    return attribute;
  }
}
