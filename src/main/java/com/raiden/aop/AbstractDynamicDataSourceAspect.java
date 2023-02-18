package com.raiden.aop;

import org.springframework.core.Ordered;

public abstract class AbstractDynamicDataSourceAspect implements Ordered {
    @Override
    public int getOrder() {
        // 该切面应当先于 @Transactional 执行
        return -1;
    }
}
