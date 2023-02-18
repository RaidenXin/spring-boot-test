package com.raiden.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2022/9/11 16:34
 */
//@WebFilter
@Slf4j
public class MyFilter implements Filter {

    private FilterConfig filterConfig;
    private String forbidIds;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        //获取初始化参数
        forbidIds = filterConfig.getInitParameter("forbidIds");
        System.out.println("forbidIds:" + forbidIds);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.error("过滤器被调用了！");
    }

    @Override
    public void destroy() {
        System.out.println("DemoFilter destroy");
    }
}
