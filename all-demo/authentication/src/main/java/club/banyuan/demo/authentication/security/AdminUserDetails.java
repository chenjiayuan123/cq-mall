package club.banyuan.demo.authentication.security;

import club.banyuan.demo.authentication.dao.entity.UmsAdmin;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminUserDetails implements UserDetails {

  private UmsAdmin admin;

  private List<? extends GrantedAuthority> authorities;

  public AdminUserDetails(UmsAdmin admin, List<? extends GrantedAuthority> authorities) {
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
}
