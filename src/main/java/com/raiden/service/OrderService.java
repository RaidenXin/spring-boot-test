package com.raiden.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.raiden.callback.RedisCurrentLimitingDegradeCallbackImpl;
import com.raiden.config.CommonConfiguration;
import com.raiden.handler.CustomerBlockHandler;
import com.raiden.model.Order;
import com.raiden.redis.current.limiter.annotation.CurrentLimiting;
import com.raiden.redis.current.limiter.annotation.DegradeCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.meter.Counter;
import org.apache.skywalking.apm.toolkit.meter.Histogram;
import org.apache.skywalking.apm.toolkit.meter.MeterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:12 2020/4/7
 * @Modified By:
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private TestService testService;

    public String getUser(String id, String name, String age, String sex){
        return "hello word";
    }

    public double testMeter(String meterName){
        log.info(testService.getClass().getName());
        try {
            Thread.sleep(Integer.parseInt(meterName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.error(meterName);
        Counter counter = MeterFactory.counter(meterName).tag("tagKey", "tagValue").mode(Counter.Mode.INCREMENT).build();
        counter.increment(1d);
        return counter.get();
    }

    @CurrentLimiting(value = "getGrade",degradeCallback = @DegradeCallback(callback = "redisCurrentLimitingDegradeCallbackImpl", callbackClass = RedisCurrentLimitingDegradeCallbackImpl.class))
    @SentinelResource(value = "testMeter2", fallbackClass = CustomerBlockHandler.class, fallback = "blocHandler")
    public double testMeter2(String meterName){
        log.error(meterName);
        Histogram histogram = MeterFactory.histogram("test").tag("tagKey", "tagValue").steps(Arrays.<Double>asList(1.0D, 5.0D, 10.0D)).minValue(0).build();
        histogram.addValue(3);
        return 1;
    }

    public Order getOrder(String orderId) {
        return null;
    }
}
