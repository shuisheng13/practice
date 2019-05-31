package com.yhh.springcloud.scloudservice.service.fallback;

import com.yhh.springcloud.api.vo.Product;
import com.yhh.springcloud.api.vo.Users;
import com.yhh.springcloud.scloudservice.service.IZUUlClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IZUUlClientServiceallbackFactory implements FallbackFactory<IZUUlClientService> {
    @Override
    public IZUUlClientService create(Throwable throwable) {
        return  new IZUUlClientService() {
            @Override
            public Product getProduct(long id) {
                Product product = new Product();
                product.setProductId(999999L);
                product.setProductName("feign-zuulName");
                product.setProductDesc("feign-zuulDesc");
                return  product;
            }

            @Override
            public List<Product> listProduct() {
                return null;
            }

            @Override
            public boolean addPorduct(Product product) {
                return false;
            }

            @Override
            public Users getUsers(String name) {
                Users user = new Users();
                user.setSex("F");
                user.setAge(17);
                user.setName("zuul-fllback："+name);
                return user;
            }
        };
    }
}
