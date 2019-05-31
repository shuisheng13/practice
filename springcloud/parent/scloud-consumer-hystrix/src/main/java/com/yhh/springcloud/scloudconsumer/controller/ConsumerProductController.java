package com.yhh.springcloud.scloudconsumer.controller;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.scloudservice.service.IProductClientService;
import com.yhh.springcloud.scloudservice.service.IZUUlClientService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerProductController {


    @Resource
    private IProductClientService iProductClientService;
    @Resource
    private IZUUlClientService izuUlClientService;

    @RequestMapping("/product/get")
    public Object getProduct(long id) {
        return  iProductClientService.getProduct(id);
    }

    @RequestMapping("/product/list")
    public  Object listProduct() {
        return iProductClientService.listProduct();
    }

    @RequestMapping("/product/add")
    public Object addPorduct(Product product) {
        return  iProductClientService.addPorduct(product);
    }

    @RequestMapping("/product/getProductAndUser")
    public Object getProductAndUser(long id) {
        Map<String,Object> result = new HashMap();
        result.put("product",izuUlClientService.getProduct(id));
        result.put("user",izuUlClientService.getUsers(id+""));
        return  result;
    }

}
