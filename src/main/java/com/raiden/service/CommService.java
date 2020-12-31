package com.raiden.service;


import com.raiden.annotation.TestService;
import com.raiden.model.Order;

@TestService
public interface CommService {
    Order getOrder(String id);
}
