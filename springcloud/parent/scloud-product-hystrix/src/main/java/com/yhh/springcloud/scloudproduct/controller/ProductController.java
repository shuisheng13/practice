package com.yhh.springcloud.scloudproduct.controller;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.scloudproduct.service.IProductService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/prodcut")
public class ProductController {

    @Resource
    private IProductService iProductService;
    @Resource
    private DiscoveryClient client ; // 进行Eureka的发现服务
    @RequestMapping(value="/get/{id}")
    @HystrixCommand(fallbackMethod = "getFallback")
    public Object get(@PathVariable("id") long id) {
        Product product = this.iProductService.get(id);
        if(product == null) {
            throw new RuntimeException("该产品已下架！") ;
        }
        return  product;
    }

    @RequestMapping(value="/test/{id}")
    public Object test(@PathVariable("id") long id) {
        Product product = this.iProductService.get(id);
        return  product;
    }

    public Object  getFallback(@PathVariable("id") long id){
        Product product = new Product();
        product.setProductName("HystrixName");
        product.setProductDesc("HystrixDesc");
        product.setProductId(0L);
        return product;
    }

    @RequestMapping(value="/add")
    public Object add(@RequestBody Product product) {
        return this.iProductService.add(product) ;
    }
    @RequestMapping(value="/list")
    public Object list() {
        return this.iProductService.list() ;
    }


    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}