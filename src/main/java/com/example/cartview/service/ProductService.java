package com.example.cartview.service;

import com.example.cartview.common.Product;
import com.example.cartview.common.ResponseBase;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 吴玥
 * @Title ProductService
 * @Description
 * @date 2020/1/14
 */
@Service
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;


    public ResponseBase<Product> findProductByPid( Integer id){
        ResponseEntity<ResponseBase> anotherExchange=  restTemplate.exchange("http://localhost:8111/apiProduct/findProductByPid/"+id, HttpMethod.GET,null,ResponseBase.class);
        return anotherExchange.getBody();
    }
    public ResponseBase<PageInfo> findAllProduct(int pageNum){
        ResponseEntity<ResponseBase> anotherExchange=  restTemplate.exchange("http://localhost:8111/apiProduct/findAllProduct?pageNum="+pageNum,HttpMethod.GET,null,ResponseBase.class);
        return anotherExchange.getBody();
    }
}
