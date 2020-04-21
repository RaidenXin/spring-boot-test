package com.raiden.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:09 2020/4/7
 * @Modified By:
 */
@Getter
@Setter
public class Order {

    private String orderId;
    private String memberId;
    private double pirce;
    private String commodityName;
    public Order(String memberId,String commodityName,double pirce){
        this.memberId = memberId;
        this.commodityName = commodityName;
        this.pirce = pirce;
        this.orderId = UUID.randomUUID().toString();
    }
}
