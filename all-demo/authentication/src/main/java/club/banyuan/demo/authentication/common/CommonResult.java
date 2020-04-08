package club.banyuan.demo.authentication.common;


public class CommonResult {

  private int code;

  private String message;

  private Object data;

  public CommonResult() {
  }

  public CommonResult(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public CommonResult(ResultCode resultCode, Object data) {
    this(resultCode.getCode(), resultCode.getMessage(), data);
  }

  public static CommonResult success(Object data) {
    return new CommonResult(ResultCode.SUCCESS, data);
  }

  public static CommonResult failed(Object data) {
    return new CommonResult(ResultCode.FAILED, data);
  }

  public static CommonResult unauthorized(Object data) {
    return new CommonResult(ResultCode.UNAUTHORIZED, data);
  }

  public static CommonResult unauthorized() {
    return new CommonResult(ResultCode.UNAUTHORIZED, ResultCode.UNAUTHORIZED.getMessage());
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }
}
