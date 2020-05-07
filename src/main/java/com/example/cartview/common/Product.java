package com.example.cartview.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private Integer pid;

    private String pname;

    private Integer price;

    private Integer inventory;

    private String picpath;

    private Integer statis;

    private String details;

    private static final long serialVersionUID = 1L;


}