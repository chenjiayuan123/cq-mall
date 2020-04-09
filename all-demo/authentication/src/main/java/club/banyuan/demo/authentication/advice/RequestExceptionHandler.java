package club.banyuan.demo.authentication.advice;

import club.banyuan.demo.authentication.common.CommonResult;
import club.banyuan.demo.authentication.common.ReqFailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

  @ExceptionHandler(ReqFailException.class)
  public CommonResult runtimeExceptionHandler(RuntimeException ex) {

    return CommonResult.failed(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResult runtimeExceptionHandler(MethodArgumentNotValidException ex) {

    return CommonResult.badRequest(ex.getMessage());
  }

}
