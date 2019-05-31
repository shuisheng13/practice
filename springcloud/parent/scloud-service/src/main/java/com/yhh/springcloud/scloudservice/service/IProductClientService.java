package com.yhh.springcloud.scloudservice.service;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.scloudservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

@FeignClient(name = "SCLOUD-PRODUCT")
/*@FeignClient(name = "SCLOUD-PRODUCT",configuration = FeignClientConfig.class,
        fallbackFactory = IProductClientServiceFallbackFactory.class)*/
//fallbackFactory = IProductClientServiceFallbackFactory.class 支持服务熔断
public interface IProductClientService {
    @RequestMapping("/prodcut/get/{id}")
    public Product getProduct(@PathVariable("id")long id);

    @RequestMapping("/prodcut/list")
    public  List<Product> listProduct() ;

    @RequestMapping("/prodcut/add")
    public boolean addPorduct(Product product) ;

}