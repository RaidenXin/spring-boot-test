package com.raiden.config;

import com.raiden.annotation.NRpcScan;
import com.raiden.aop.annotation.ConfigValue;
import com.raiden.aop.annotation.I18nConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:45 2020/4/8
 * @Modified By:
 */
@Configuration
@Getter
@Setter
@I18nConfig
@NRpcScan(basePackage = "com.raiden.service")
public class Config {

    @ConfigValue("order.commodityName.{language}")
    private String commodityName;
    @ConfigValue("order.price")
    private double price;


}
