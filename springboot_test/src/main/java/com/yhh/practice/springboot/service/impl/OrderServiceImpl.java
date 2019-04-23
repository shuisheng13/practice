package com.yhh.practice.springboot.service.impl;

import com.yhh.practice.springboot.dao.orders.OrdersMapper;
import com.yhh.practice.springboot.dao.users.UsersMapper;
import com.yhh.practice.springboot.model.Orders;
import com.yhh.practice.springboot.model.Users;
import com.yhh.practice.springboot.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl  implements IOrderService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    public void addOrder(Orders orders, Users users) {
        usersMapper.insertSelective(users);
        //int i=10/0;
        ordersMapper.insertSelective(orders);
    }

}

