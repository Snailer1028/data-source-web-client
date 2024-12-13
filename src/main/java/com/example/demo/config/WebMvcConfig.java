package com.example.demo.config;

import com.example.demo.interceptor.ApiAccessLogInterceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录校验
                    SaRouter.match("/**").check(r -> StpUtil.checkLogin());
                    // 角色校验
                    SaRouter.match("/user/**", r -> StpUtil.checkRoleOr("ROLE_user"));
                    // 权限校验
                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("/user/*"));
                }))
                .addPathPatterns("/**")
                // .excludePathPatterns("/user/login");
                .excludePathPatterns("/**");
    }

    /**
     * 跨域配置
     *
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
