package com.raiden.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:30 2021/12/13
 * @Modified By:
 */
@Slf4j
public class SessionHandlerInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.error("这里是 SessionHandlerInterceptor 拦截器");
        request.getSession();
        return true;
    }
}
