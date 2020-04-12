package club.banyuan.mall.mgt.common;

public enum ResultCode {
  SUCCESS(200, "操作成功"),
  BAD_REQUEST(400, "请求失败"),
  UNAUTHORIZED(401, "用户未登陆或token过期"),
  FORBIDDEN(403, "没有相关权限"),
  FAILED(500, "内部异常");

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
