package com.example.cartview.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    private Integer cid;

    private Integer pid;

    private String pname;

    private Integer choice;

    private Product product;

    private static final long serialVersionUID = 2L;

}