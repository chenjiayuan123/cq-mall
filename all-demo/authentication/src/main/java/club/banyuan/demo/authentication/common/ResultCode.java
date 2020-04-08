package club.banyuan.demo.authentication.common;

public enum ResultCode {
  SUCCESS(200, "操作成功"),
  UNAUTHORIZED(401, "用户未登陆或token过期"),
  FAILED(500, "操作失败，内部异常");

  private int code;
  private String message;

  ResultCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
