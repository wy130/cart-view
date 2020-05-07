package com.example.cartview.controller;

import com.example.cartview.common.*;
import com.example.cartview.service.CartService;
import com.example.cartview.service.ConsumerService;
import com.example.cartview.service.ProductService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 吴玥
 * @Title ViewController
 * @Description
 * @date 2020/1/6
 */
@RequestMapping("/view")
@Controller
public class ViewController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private CartService cartService;

    /**
     * 去登录页面
     *
     * @param request
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(HttpServletRequest request) {
        return "login";
    }

    /**
     * 检验图片验证码
     */
    @PostMapping("/checkImageCode")
    @ResponseBody
    public String checkImageCode(String imagecode, HttpServletRequest request) {
        System.out.println("校验图片验证码");
        HttpSession session = request.getSession();
        // 获取 session中的验证码
        String sessionCode = (String) session.getAttribute("imgCode");
        System.out.println(sessionCode);

        if (null == sessionCode || "".equals(sessionCode) || null == imagecode || "".equals(imagecode)) {

            return "{\"msg\":\"no\"}";
        } else if (imagecode.equalsIgnoreCase(sessionCode)) {

            return "{\"msg\":\"ok\"}";
        }

        return "{\"msg\":\"no\"}";
    }

    /**
     * 点击查看商品详情
     *
     * @param pid
     * @return
     */
    @GetMapping("/findProductByPid/{pid}")
    public String findProductByPid(HttpServletRequest request, @PathVariable("pid") int pid) {
        ResponseBase<Product> productResponseBase = productService.findProductByPid(pid);
        if (productResponseBase.getRtnCode() == 200) {
            request.setAttribute("product", productResponseBase.getT());
            return "productdetail";
        }
        return "error";
    }

    /*
     * 登录功能实现
     */
    @PostMapping("/login")
    public String login(Consumer consumer, String imagecode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 获取 session中的验证码
        String sessionCode = (String) session.getAttribute("imgCode");
        // 获取表单中的验证码
        if (null == sessionCode || "".equals(sessionCode) || null == imagecode || "".equals(imagecode)) {
            request.setAttribute("codeErr", "验证码为空!");
            return "login";
        } else if (imagecode.equalsIgnoreCase(sessionCode)) {
            //验证码校验成功 开始登陆
            Result<Consumer> consumerResult = consumerService.loginByJwt(consumer.getCnickname(), consumer.getCpassword(), request);
            if (consumerResult.getCode() == ResultCode.SUCCESS.code()) {

                attr.addFlashAttribute("consumerName", consumer.getCnickname());

                String token = (String) consumerResult.getData();
                //将生成的token数据放入cookie
                Cookie cookie = new Cookie("token", token);
                cookie.setDomain("localhost");
                cookie.setPath("/");
                response.addCookie(cookie);

                return "redirect:/view/showAllProduct";
            }
        }
        return "error";
    }

    /**
     * @param request
     * @param pageNum
     * @return
     */
    @GetMapping("/showAllProduct")
    public String showAllProduct(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum) {
        ResponseBase<PageInfo> productResult = productService.findAllProduct(pageNum);
        if (productResult.getRtnCode() == 200) {
            JSONObject jsonObject = JSONObject.fromObject(productResult.getT());
            PageInfo<Product> pageInfo = (PageInfo<Product>) JSONObject.toBean(jsonObject, PageInfo.class);
            JSONArray JSONarray = JSONArray.fromObject(pageInfo.getList());
            List<Product> products = (List<Product>) JSONArray.toCollection(JSONarray, Product.class);
            pageInfo.setList(products);
            if (pageInfo != null) {
                request.setAttribute("pageInfo", pageInfo);
            }
            String consumerName = (String) request.getAttribute("consumerName");
            if (consumerName != null) {
                System.out.println(consumerName);
                return "product";
            }
            request.setAttribute("consumerName", "您当前是游客登录");

            return "product";
        }
        return "error";
    }

    /**
     * 将商品加入购物车
     *
     * @param token
     * @param pid
     * @return
     */
    @GetMapping("/AddProductToCart/{pid}")
    @ResponseBody
    public String AddProductToCart(@RequestHeader("Authorization") String token, @PathVariable("pid") int pid) {
        //根据请求头的token数据获取当前登录用户信息，将对应的pid商品加入该用户的购物车表
        ResponseBase<Cart> cartResponseBase = cartService.AddProductToCart(token, pid);
        if (cartResponseBase.getRtnCode() == 200) {
            return "{\"msg\":\"ok\"}";
        } else {
            return "{\"msg\":\"no\"}";
        }

    }

    /**
     * 查询当前用户的所有购物车商品项
     *
     * @param token
     * @return
     */
    @GetMapping("/getCart")
    @ResponseBody
    public List<Cart> toCart(HttpServletRequest request, @RequestHeader("Authorization") String token) {
        //根据请求头中的token去查询当前用户的购物车
        ResponseBase<Cart> cartResponseBase = cartService.findAllCartItems(token);
        //查询成功，返回查询出的购物车商品list给前端
        if (cartResponseBase.getRtnCode() == 200) {
            return (List<Cart>) cartResponseBase.getT();
        }
        return null;
    }

    /**
     * 是否可以进入购物车
     *
     * @param token
     * @param request
     * @return
     */
    @GetMapping("/toCart")
    @ResponseBody
    public String toCart(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        //如果请求头中存在token数据，默认存在登录用户，可以前往购物车页面
        if (token.length() > 10) {
            return "{\"msg\":\"ok\"}";
        } else {
            return "{\"msg\":\"no\"}";
        }

    }

    /**
     * 跳转购物车页面
     *
     * @param request
     * @return
     */
    @GetMapping("/cart")
    public String cart(HttpServletRequest request) {
        return "cart";
    }

    /**
     * 将购物车中的商品项移除
     *
     * @param token
     * @param pid
     * @return
     */
    @GetMapping("/RemoveProductFromCart/{pid}")
    @ResponseBody
    public String RemoveProductFromCart(@RequestHeader("Authorization") String token, @PathVariable("pid") int pid) {
        ResponseBase<Cart> cartResponseBase = cartService.RemoveProductFromCart(token, pid);
        if (cartResponseBase.getRtnCode() == 200) {
            return "{\"msg\":\"ok\"}";
        }
        return "{\"msg\":\"no\"}";
    }
}
