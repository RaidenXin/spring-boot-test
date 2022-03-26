package com.raiden.callback;

import com.raiden.model.Grade;
import com.raiden.redis.current.limiter.callbock.RedisCurrentLimitingDegradeCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 17:47 2022/1/16
 * @Modified By:
 */
@Component("redisCurrentLimitingDegradeCallbackImpl")
public class RedisCurrentLimitingDegradeCallbackImpl implements RedisCurrentLimitingDegradeCallback {
    @Override
    public <T> T callback() {
        List<Grade> grades = new ArrayList<>();
        Grade grade = new Grade();
        grade.setId(1);
        grade.setUserName("这是个假数据");
        grades.add(grade);
        return (T) grades;
    }
}
