package com.yhh.springcloud.scloudproduct.product;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.scloudproduct.ScloudProductApplication3;
import com.yhh.springcloud.scloudproduct.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = ScloudProductApplication3.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Resource
    private IProductService iProductService;
    @Test
    public void testGet() {
        System.out.println(iProductService.get(1));
    }
    @Test
    public void testAdd() {
        Product dept = new Product() ;
        dept.setProductName("lison-" + System.currentTimeMillis());
        System.out.println(iProductService.add(dept));
    }
    @Test
    public void testList() {
        System.out.println(iProductService.list());
    }
}