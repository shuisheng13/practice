package com.yhh.springcloud.scloudservice.service;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.api.vo.Users;
import com.yhh.springcloud.scloudservice.config.FeignClientConfig;
import com.yhh.springcloud.scloudservice.service.fallback.IZUUlClientServiceallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
//@FeignClient(name = "SCLOUD-ZUUL")
@FeignClient(name = "SCLOUD-ZUUL",configuration = FeignClientConfig.class,
        fallbackFactory = IZUUlClientServiceallbackFactory.class)
public interface IZUUlClientService {

    @RequestMapping("/wecloud/product/prodcut/get/{id}")
    public Product getProduct(@PathVariable("id")long id);

    @RequestMapping("/wecloud/product/prodcut/list")
    public List<Product> listProduct() ;

    @RequestMapping("/wecloud/product/prodcut/add")
    public boolean addPorduct(Product product) ;

    @RequestMapping("/wecloud/user/users/get/{name}")
    public Users getUsers(@PathVariable("name")String name);
}
