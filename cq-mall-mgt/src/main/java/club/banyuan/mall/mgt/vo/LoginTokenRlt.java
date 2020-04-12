package club.banyuan.mall.mgt.vo;

public class LoginTokenRlt {

  private String tokenHead;
  private String token;

  public LoginTokenRlt() {
  }

  public LoginTokenRlt(String tokenHead, String token) {
    this.tokenHead = tokenHead;
    this.token = token;
  }

  public String getTokenHead() {
    return tokenHead;
  }

  public void setTokenHead(String tokenHead) {
    this.tokenHead = tokenHead;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
