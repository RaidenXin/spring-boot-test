package com.raiden.controller;

import com.raiden.model.Order;
import com.raiden.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:08 2020/4/7
 * @Modified By:
 */
@RestController
@RequestMapping("Order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/getOrder/{language}")
    public Order getOrder(@RequestParam(name = "orderId")String orderId){
        return orderService.getOrder(orderId);
    }
}
