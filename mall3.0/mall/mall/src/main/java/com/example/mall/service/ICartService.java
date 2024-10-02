package com.example.mall.service;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */
public interface ICartService extends IService<Cart> {


    ResponseResult getCart(Integer id);

    ResponseResult insertCart(Cart cart);

    ResponseResult updateCart(Cart cart);

    ResponseResult delete(List<Integer> ids);

    ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize);

}
