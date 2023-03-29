package com.raiden.signer;

import com.raiden.exception.SignatureException;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 校验签名的策略接口
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2023/3/29 23:47
 */
public interface SignatureStrategy {

    void verifySignature(ProceedingJoinPoint joinPoint) throws SignatureException;

}
