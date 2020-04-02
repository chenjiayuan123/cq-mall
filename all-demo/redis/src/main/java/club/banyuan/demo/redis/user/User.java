package club.banyuan.demo.redis.user;

import java.util.Objects;

public class User {

  private String name;
  private String pwd;

  // 必须提供无参的构造方法给反序列化代码使用
  public User() {
  }

  public User(String name, String pwd) {
    this.name = name;
    this.pwd = pwd;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(name, user.name) &&
        Objects.equals(pwd, user.pwd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, pwd);
  }
}
