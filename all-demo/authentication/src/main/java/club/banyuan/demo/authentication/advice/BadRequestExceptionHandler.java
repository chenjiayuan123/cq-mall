package club.banyuan.demo.authentication.advice;

import club.banyuan.demo.authentication.common.CommonResult;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestExceptionHandler {
    /**
     *  校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult validationBodyException(MethodArgumentNotValidException exception){

        BindingResult result = exception.getBindingResult();
      /**
      *可解开
      **/
      //  if (result.hasErrors()) {
      //      List<ObjectError> errors = result.getAllErrors();
      //      errors.forEach(p ->{
      //          FieldError fieldError = (FieldError) p;
      //          logger.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
      //                  "},errorMessage{"+fieldError.getDefaultMessage()+"}");
      //      });
      //  }
      //返回自定义错误格式
      //fieldError.getDefaultMessage()为实体类中的message
        return CommonResult.failed(exception.getMessage());
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public CommonResult parameterTypeException(HttpMessageConversionException exception){
        return CommonResult.failed("类型转换错误");
    }
}