package com.example.cartview.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumer implements Serializable {
    private Integer cid;

    private String cname;

    private String cnickname;

    private String cpassword;

    private String cphone;

    private String address;

    private Integer statis;

//    private Set<Permission> permissions = new HashSet<Permission>(0);//角色与模块  多对多
    private String permissions;//用户的权限

    private static final long serialVersionUID = 5L;

    public String getPermissions(){
         return  "AddProductToCart,RemoveProductFromCart,profile,findProductByPid,findAllProduct,findAllCartItem";

    }



}