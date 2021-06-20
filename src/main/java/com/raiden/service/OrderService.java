package com.raiden.service;

import com.raiden.aop.annotation.CustomService;
import com.raiden.config.Config;
import com.raiden.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.meter.Counter;
import org.apache.skywalking.apm.toolkit.meter.Histogram;
import org.apache.skywalking.apm.toolkit.meter.MeterFactory;
import org.apache.skywalking.apm.toolkit.meter.MeterId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
@CustomService
public class OrderService {

    @Autowired
    private Config config;

    public String getUser(String id, String name, String age, String sex){
        return "hello word";
    }

    public double testMeter(String meterName){
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
