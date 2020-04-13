package club.banyuan.mall.mgt.dto;

import club.banyuan.mall.mgt.dao.entity.UmsAdmin;
import club.banyuan.mall.mgt.dao.entity.UmsRole;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminUserDetails implements UserDetails {

  private UmsAdmin admin;

  private List<UmsRole> roles;

  private List<? extends GrantedAuthority> authorities;

  public AdminUserDetails(UmsAdmin admin,
      List<? extends GrantedAuthority> authorities) {
    this.admin = admin;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return admin.getPassword();
  }

  @Override
  public String getUsername() {
    return admin.getPassword();
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
    return false;
  }

  public UmsAdmin getAdmin() {
    return admin;
  }

  public void setAdmin(UmsAdmin admin) {
    this.admin = admin;
  }

  public List<UmsRole> getRoles() {
    return roles;
  }

  public void setRoles(List<UmsRole> roles) {
    this.roles = roles;
  }

  public void setAuthorities(
      List<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
}
