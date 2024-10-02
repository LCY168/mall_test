package com.example.mall.domain.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartVo {
    //购物车id
    private Integer id;

    /**
     * 商品id
     */
    private Integer prodId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 商品标题
     */
    private String prodName;
    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品价格
     */
    private BigDecimal price;

}
