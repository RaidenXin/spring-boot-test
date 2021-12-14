package com.raiden.config;

import com.raiden.interceptor.SessionHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:31 2021/12/13
 * @Modified By:
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/emp/toLogin","/emp/login","/js/**","/css/**","/images/**");
    }

}
