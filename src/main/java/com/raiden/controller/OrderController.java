package com.raiden.controller;

import com.raiden.aop.annotation.CurrentLimiting;
import com.raiden.model.Order;
import com.raiden.service.CacheService;
import com.raiden.service.OrderService;
import com.raiden.service.WrapService;
import com.raiden.task.DynamicScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:08 2020/4/7
 * @Modified By:
 */
@RestController
@RequestMapping("Order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private WrapService wrapService;


    @GetMapping("/getOrder/{language}")
    public Order getOrder(@RequestParam(name = "orderId")String orderId){
        return orderService.getOrder(orderId);
    }

    @CurrentLimiting
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

    @GetMapping("/test3")
    public String test3(@NotBlank(message = "不能为空") String name){
        return wrapService.warp(name);
    }
}
