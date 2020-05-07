package com.example.cartview.service;

import com.example.cartview.common.Consumer;
import com.example.cartview.common.ResponseBase;
import com.example.cartview.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴玥
 * @Title ConsumerService
 * @Description
 * @date 2020/1/14
 */
@Service
public class ConsumerService {
    @Autowired
    private RestTemplate restTemplate;


    public Result<Consumer> loginByJwt(String cnickname, String cpassword, HttpServletRequest request){
        String url="http://localhost:8111/apiConsumer/loginByJwt?cnickname="+cnickname+"&cpassword="+cpassword;
        System.out.println(url);
        return restTemplate.getForObject("http://localhost:8111/apiConsumer/loginByJwt?cnickname="+cnickname+"&cpassword="+cpassword,Result.class);
    }
    public Result<Consumer> profile(){
        return restTemplate.getForObject("http://localhost:8111/apiConsumer/profile",Result.class);
    }
}
