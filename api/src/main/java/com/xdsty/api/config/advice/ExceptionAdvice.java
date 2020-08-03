package com.xdsty.api.config.advice;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.api.common.Result;
import com.xdsty.api.common.ResultCode;
import com.xdsty.api.common.exceptions.AccessTokenExpiredException;
import com.xdsty.api.common.exceptions.TokenExpiredException;
import com.xdsty.api.util.RequestContextHolder;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    private static final String LINE_BREAK = "\n";

    /**
     * 400 数据校验异常
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        List<String> errorMessages = Optional.ofNullable(ex.getConstraintViolations()).orElse(new HashSet<>()).stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String errorMessage = StringUtils.join(errorMessages, LINE_BREAK);
        log.error(errorMessage, ex);
        return Result.failure(errorMessage);
    }

    /**
     * 400 controller数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        String errorMessage = StringUtils.join(errorMessages, LINE_BREAK);
        log.error(errorMessage, ex);
        return Result.failure(errorMessage);
    }

    /**
     * 400 controller参数类型异常
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> methodArgumentTypeMismatchExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure(ex.getMessage());
    }

    /**
     * 400 controller参数缺失异常
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> httpMessageNotReadableExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure(ex.getMessage());
    }

    /**
     * 400 Request method 'xxx' not supported
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure(ex.getMessage());
    }

    /**
     * 404 找不到URL路径
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> noFoundExceptionHandler(NoHandlerFoundException ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure(ex.getMessage());
    }

    @ExceptionHandler(value = {BusinessRuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> businessExceptionHandler(BusinessRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure(ex.getMessage());
    }

    /**
     * token过期异常
     */
    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void tokenExpiredException(ExpiredJwtException ex) {
        log.error("token过期，重新登录", ex);
        HttpServletResponse response = RequestContextHolder.getServletResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * token失效异常
     */
    @ExceptionHandler(value = {TokenExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void tokenRefreshException(TokenExpiredException ex) {
        log.error("token失效，重新登录", ex);
        HttpServletResponse response = RequestContextHolder.getServletResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * accessToken过期
     */
    @ExceptionHandler(value = {AccessTokenExpiredException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result accessTokenExpiredException(AccessTokenExpiredException ex) {
        log.error("accessToken过期，重新请求", ex);
        return Result.failure(ResultCode.SIGNATURE);
    }

    /**
     * 500
     * Throwable 未知异常
     *
     * @param ex Throwable
     * @return ResponseInfo
     */
    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> throwableHandler(Throwable ex) {
        log.error(ex.getMessage(), ex);
        return Result.failure("服务器内部错误");
    }

}