package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;
import com.example.mall.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;
    @GetMapping("/list")
    public ResponseResult getCartList(@RequestParam(required = false) String userId,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
        return cartService.getCartList(userId,pageNum,pageSize);
    }

    @GetMapping("/admin/{id}")
    public ResponseResult getCart(@PathVariable Integer id){
        return cartService.getCart(id);

    }
    @PostMapping("admin/insert")
    public ResponseResult insertCart(@RequestBody Cart cart){
        return cartService.insertCart(cart);

    }
    @PostMapping("/admin/update")
    public ResponseResult updateCart(@RequestBody Cart cart){
        return  cartService.updateCart(cart);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteCart(@RequestBody List<Integer> ids){
        return  cartService.delete(ids);
    }
}
