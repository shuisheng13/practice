package com.yhh.practice.springboot.service;

import com.yhh.practice.springboot.model.Orders;
import com.yhh.practice.springboot.model.Users;

public interface IOrderService {
    void addOrder(Orders orders, Users users);
}
