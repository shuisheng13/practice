package com.yhh.practice.springboot.test;

import com.yhh.practice.springboot.SpringbootApplication;
import com.yhh.practice.springboot.model.Orders;
import com.yhh.practice.springboot.model.Users;
import com.yhh.practice.springboot.service.IOrderService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = {SpringbootApplication.class})
@RunWith(SpringRunner.class)
public class Test {

    @Resource
    private IOrderService iOrderService;

    @org.junit.Test
    public void test1() {
        Users users = new Users();
        users.setUsername("enjoy");
        users.setPasswd("123");
        users.setId(1);

        Orders orders = new Orders();
        orders.setAccount(12);
        orders.setName("娃娃");
        orders.setUserId(1);
        iOrderService.addOrder(orders,users);
    }
}
