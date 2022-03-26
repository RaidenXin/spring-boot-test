package com.raiden.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.raiden.callback.RedisCurrentLimitingDegradeCallbackImpl;
import com.raiden.handler.CustomerBlockHandler;
import com.raiden.mapper.TestTbGradeMapper;
import com.raiden.model.Grade;
import com.raiden.redis.current.limiter.RedisCurrentLimiter;
import com.raiden.redis.current.limiter.annotation.CurrentLimiting;
import com.raiden.redis.current.limiter.annotation.DegradeCallback;
import com.raiden.service.InstanceService;
import com.raiden.model.Order;
import com.raiden.model.User;
import com.raiden.service.CacheService;
import com.raiden.service.OrderService;
import com.raiden.task.DynamicScheduledTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:08 2020/4/7
 * @Modified By:
 */
@RestController
@RequestMapping("order")
@Validated
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired(required = false)
    private CacheService cacheService;

    @ModelAttribute
    public void modelAttribute(){
        log.error("这里是 ModelAttribute 方法");
    }

    @GetMapping("/getOrder/{language}")
    public Order getOrder(@RequestParam(name = "orderId")String orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("/getUser/{language}")
    public String getUser(@RequestParam(name = "id")String id,
                          @RequestParam(name = "name")String name,
                          @RequestParam(name = "age")String age,
                          @RequestParam(name = "sex")String sex){
        return orderService.getUser(id, name, age, sex);
    }

    @GetMapping("/clearCache")
    public void clearCache(String key){
        cacheService.clearCache(key);
    }

    @Autowired
    private DynamicScheduledTask dynamicScheduledTask;

    @GetMapping("/test1")
    public void test1(String cron){
        try {
            String decode = URLDecoder.decode(cron, "UTF-8");
            dynamicScheduledTask.setCron(decode);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test2")
    public String test2(@NotBlank(message = "不能为空") String name){
        return name;
    }

    @GetMapping("/testMeter")
    public double testMeter(String meterName) {
        return orderService.testMeter(meterName);
    }

    @GetMapping("/testMeter2")
    public double testMeter2(String meterName){
        return orderService.testMeter(meterName);
    }

    @GetMapping("/testSentinel")
    @SentinelResource(value = "testSentinel", fallbackClass = CustomerBlockHandler.class, fallback = "blocHandler")
    public String testSentinel(){
        return "seccess";
    }


    @Autowired
    private TestTbGradeMapper mapper;

    @GetMapping("/getGrade")
    @CurrentLimiting(value = "getGrade",degradeCallback = @DegradeCallback(callback = "redisCurrentLimitingDegradeCallbackImpl", callbackClass = RedisCurrentLimitingDegradeCallbackImpl.class))
    public List<Grade> getGrade(String username){
        return mapper.query(username);
    }


    @Autowired
    private InstanceService instanceService;

    @GetMapping("/disable")
    public String disable(){
        boolean disable = instanceService.disable();
        return "seccess";
    }

    @GetMapping("/enable")
    public String enable(){
        boolean enable = instanceService.enable();
        return "seccess";
    }

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/request")
    public String request(){
        String method = request.getMethod();
        return method;
    }
}
