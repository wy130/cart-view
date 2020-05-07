package com.example.cartview.service;

import com.example.cartview.common.Cart;
import com.example.cartview.common.ResponseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

/**
 * @author 吴玥
 * @Title CartService
 * @Description
 * @date 2020/1/14
 */
@Service
public class CartService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * restTemplate发送get请求,携带header,封装的统一方法
     * @param token
     * @return
     */
    public HttpEntity setHeader(String token){
        //设置请求头参数
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);

        HttpEntity request = new HttpEntity(requestHeaders);
        return request;
    }

    /**
     * 将商品加入购物车
     * @param token
     * @param id
     * @return
     */
    public ResponseBase AddProductToCart(String token,Integer id){
        HttpEntity request = setHeader(token);
        ResponseEntity<ResponseBase> anotherExchange =restTemplate.exchange("http://localhost:8111/apiCart/AddProductToCart/"+id,HttpMethod.GET,request,ResponseBase.class);
        return anotherExchange.getBody();
    }

    /**
     * 将购物车中的商品项移除
     * @param token
     * @param id
     * @return
     */
    public ResponseBase<Cart> RemoveProductFromCart(String token, Integer id){
        HttpEntity request = setHeader(token);
        ResponseEntity<ResponseBase> anotherExchange =restTemplate.exchange("http://localhost:8111/apiCart/RemoveProductFromCart/"+id,HttpMethod.GET,request,ResponseBase.class);
        return anotherExchange.getBody();
    }

    /**
     * 查询当前用户的所有购物车商品项
     * @param token
     * @return
     */
    public ResponseBase<Cart> findAllCartItems( String token){
        HttpEntity request = setHeader(token);
        ResponseEntity<ResponseBase> anotherExchange =restTemplate.exchange("http://localhost:8111/apiCart/findAllCartItem", HttpMethod.GET,request, ResponseBase.class);
        return anotherExchange.getBody();
    }

}
