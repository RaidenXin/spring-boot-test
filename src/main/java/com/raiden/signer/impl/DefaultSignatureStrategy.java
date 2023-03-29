package com.raiden.signer.impl;

import com.raiden.exception.SignatureException;
import com.raiden.signer.SignatureStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认签名校验策略类
 *
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2023/3/30 0:09
 */
@Component
@Slf4j
public class DefaultSignatureStrategy implements SignatureStrategy {

    @Override
    public void verifySignature(ProceedingJoinPoint joinPoint) throws SignatureException {
        // 获取请求参数
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String param1 = request.getParameter("param1");
        String param2 = request.getParameter("param2");
        log.error("param1:{}", param1);
        log.error("param2:{}", param2);
    }
}
