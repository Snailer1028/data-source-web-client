package com.example.demo.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * API 访问日志 Interceptor
 * 目的：在非 prod 环境时，打印 request 和 response 两条日志到日志文件（控制台）中。
 */
@Slf4j
@SuppressWarnings("NullableProblems")
public class ApiAccessLogInterceptor implements HandlerInterceptor {
    private static final String ATTRIBUTE_STOP_WATCH = "ApiAccessLogInterceptor.StopWatch";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isNotProd()) {
            // 使用 ContentCachingRequestWrapper 封装请求, 防止getReader() has already been called for this request
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }

            // 获取请求参数和请求体
            Map<String, String[]> queryString = request.getParameterMap();
            String requestBody = this.isJsonRequest(request) ?
                    this.getRequestBody((ContentCachingRequestWrapper) request) : null;

            // 打印请求日志
            String url = request.getRequestURI();
            if (CollUtil.isEmpty(queryString) && StrUtil.isEmpty(requestBody)) {
                log.info("[开始请求 URL({}) 无参数][preHandle]", url);
            } else {
                log.info("[开始请求 URL({}) 参数({})][preHandle]", url, StrUtil.blankToDefault(requestBody,
                        queryString.toString()));
            }
            // 计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            request.setAttribute(ATTRIBUTE_STOP_WATCH, stopWatch);

            // 打印 Controller 路径
            printHandlerMethodPosition(handler);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        // 打印 response 日志
        if (isNotProd()) {
            StopWatch stopWatch = (StopWatch) request.getAttribute(ATTRIBUTE_STOP_WATCH);
            if (stopWatch != null) {
                stopWatch.stop();
                log.info("[完成请求 URL({}) 耗时({} ms)][afterCompletion]",
                        request.getRequestURI(), stopWatch.getTotalTimeMillis());
            }
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        try {
            byte[] buf = request.getContentAsByteArray();
            return new String(buf, request.getCharacterEncoding());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        return StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    private boolean isNotProd() {
        return !"prod".equals(SpringUtil.getActiveProfile());
    }

    private void printHandlerMethodPosition(Object handler) {
        HandlerMethod handlerMethod = handler instanceof HandlerMethod ? (HandlerMethod) handler : null;
        if (handlerMethod == null) {
            return;
        }
        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        try {
            // 获取 method 的 lineNumber
            List<String> clazzContents = FileUtil.readUtf8Lines(
                    ResourceUtil.getResource(null, clazz).getPath().replace("/target/classes/", "/src/main/java/")
                            + clazz.getSimpleName() + ".java");
            Optional<Integer> lineNumber = IntStream.range(0, clazzContents.size())
                    .filter(i -> clazzContents.get(i).contains(" " + method.getName() + "(")) // 简单匹配，不考虑方法重名
                    .mapToObj(i -> i + 1) // 行号从 1 开始
                    .findFirst();
            if (!lineNumber.isPresent()) {
                return;
            }
            // 打印结果
            System.out.printf("当前Controller方法路径: %s(%s.java:%d)\n", clazz.getName(), clazz.getSimpleName(),
                    lineNumber.get());
        } catch (Exception ignore) {
            // 忽略异常。原因：仅仅打印，非重要逻辑
        }
    }

    private void printHandlerMethodPosition2(Object handler) {
        if (handler instanceof HandlerMethod ) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            Class<?> clazz = method.getDeclaringClass();
            log.info("当前Controller方法: {}.{}", clazz.getSimpleName(), method.getName());
        }
    }
}
