package com.example.demo.exception;

import static com.example.demo.util.ResponseUtils.exception;

import com.example.demo.model.entity.R;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理 SpringMVC 请求参数缺失
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.error("[missingServletRequestParameterExceptionHandler]", ex);
        return exception(HttpStatus.HTTP_BAD_REQUEST, String.format("请求参数缺失: %s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error("[methodArgumentTypeMismatchExceptionHandler]", ex);
        return exception(HttpStatus.HTTP_BAD_REQUEST, String.format("请求参数类型错误: %s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return exception(HttpStatus.HTTP_BAD_REQUEST, String.format("请求参数不正确: %s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public R<?> bindExceptionHandler(BindException ex) {
        log.error("[bindExceptionHandler]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return exception(HttpStatus.HTTP_BAD_REQUEST, String.format("请求参数不正确: %s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.error("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return exception(HttpStatus.HTTP_BAD_REQUEST, String.format("请求参数不正确: %s", constraintViolation.getMessage()));
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        log.error("[noHandlerFoundExceptionHandler]", ex);
        return exception(HttpStatus.HTTP_NOT_FOUND, String.format("请求地址不存在: %s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.error("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return exception(HttpStatus.HTTP_BAD_METHOD, String.format("请求方法不正确: %s", ex.getMessage()));
    }

    /**
     * 处理 Spring Security 权限不足的异常
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public R<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.error("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]", "userid", req.getRequestURL(), ex);
        return exception(HttpStatus.HTTP_FORBIDDEN, "No permission");
    }

    /**
     * 处理业务异常 ServiceException
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public R<?> serviceExceptionHandler(ServiceException ex) {
        // 即使打印，也只打印第一层 StackTraceElement，并且使用 warn 在控制台输出，更容易看到
        try {
            StackTraceElement[] stackTraces = ex.getStackTrace();
            for (StackTraceElement stackTrace : stackTraces) {
                log.error("[serviceExceptionHandler]: {}\n{}", ex.getMsg(), stackTrace);
                break;
            }
        } catch (Exception ignored) {
            // 忽略日志，避免影响主流程
        }
        return exception(ex.getCode(), ex.getMsg());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = NullPointerException.class)
    public R<?> nullPointerExceptionHandler(NullPointerException e) {
        // 即使打印，也只打印第一层 StackTraceElement，并且使用 warn 在控制台输出，更容易看到
        try {
            StackTraceElement[] stackTraces = e.getStackTrace();
            for (StackTraceElement stackTrace : stackTraces) {
                log.error("[nullPointerExceptionHandler], 异常类型:{}\n空指针方法路径: {}",
                        e.getClass().getTypeName(),
                        stackTrace);
                break;
            }
        } catch (Exception ignored) {
            // 忽略日志，避免影响主流程
        }
        return exception(HttpStatus.HTTP_INTERNAL_ERROR, "服务内部异常");
    }

    /**
     * 处理唯一键值重复
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public R<?> duplicateKeyExceptionHandler(DuplicateKeyException ex) {
        log.error("[duplicateKeyExceptionHandler], 原因: {}", ex.getMessage());
        return exception(HttpStatus.HTTP_BAD_REQUEST, "请检查是否重复添加");
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public R<?> defaultExceptionHandler(Throwable ex) {
        log.error("[全局异常捕获], 异常类型:{}, 原因:{}", ex.getClass().getTypeName(), ex.getMessage());
        return exception(HttpStatus.HTTP_INTERNAL_ERROR,
                ex.getClass().getTypeName().concat(", ").concat(ex.getMessage()));
    }

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex 异常
     * @return 通用返回
     */
    public R<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler((NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof NullPointerException) {
            return nullPointerExceptionHandler((NullPointerException) ex);
        }
        if (ex instanceof DuplicateKeyException) {
            return duplicateKeyExceptionHandler((DuplicateKeyException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(ex);
    }
}
