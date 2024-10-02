package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.dao.CartMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;
import com.example.mall.domain.vo.CartVo;
import com.example.mall.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.util.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize) {
        Page<Cart> cartPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<Cart>().eq(userId!=null, Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectPage(cartPage, cartLambdaQueryWrapper).getRecords();
        List<CartVo> cartVos = BeanCopyUtils.copyBeanList(carts, CartVo.class);
        for (CartVo cartVo:cartVos ){
            cartVo.setProdName(productMapper.selectById(cartVo.getProdId()).getTitle());
            cartVo.setImg(productMapper.selectById(cartVo.getProdId()).getImg());
            cartVo.setPrice(productMapper.selectById(cartVo.getProdId()).getPrice());
        }
        return ResponseResult.okResult(cartVos);
    }


    @Override
    public ResponseResult getCart(Integer id) {
        Cart cart = cartMapper.selectById(id);
        if (cart ==null){
            return ResponseResult.failResult("找不到对应的图片");
        }else {
            CartVo cartVo =new CartVo();
            BeanUtils.copyProperties(cart,cartVo);
            cartVo.setProdName(productMapper.selectById(cartVo.getProdId()).getTitle());
            cartVo.setImg(productMapper.selectById(cartVo.getId()).getImg());
            cartVo.setPrice(productMapper.selectById(cartVo.getId()).getPrice());
            return ResponseResult.okResult(cartVo);
        }

    }


    @Override
    public ResponseResult insertCart(Cart cart) {
        if (cart.getUserId() ==null||cart.getProdId() ==null){
            return ResponseResult.failResult("商品id和用户id不能为空");
        }
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, cart.getUserId()).eq(Cart::getProdId,cart.getProdId());
        Cart cart1 = cartMapper.selectOne(queryWrapper);
        /*如果为空则新增一条记录,如果有相同商品则增加数量*/
        if (cart1 ==null){
            int res = cartMapper.insert(cart);
            if(res ==1){
                return ResponseResult.okResult("新增一条购物车记录");
            }
            else{
                return ResponseResult.failResult("修改失败");
            }
        }else {
           cart1.setNum(cart1.getNum()+cart.getNum());
            int res =cartMapper.updateById(cart1);
            if(res ==1){
                return ResponseResult.okResult("修改成功");
            }
            else{
                return ResponseResult.failResult("修改失败");
            }
        }

    }

    @Override
    public ResponseResult updateCart(Cart cart) {
        if (cart.getId() == null){
            return ResponseResult.failResult("商品id不能为空");
        }
        int res =cartMapper.updateById(cart);
        if(res ==1){
            return ResponseResult.okResult("修改成功");
        }
        else{
            return ResponseResult.failResult("修改失败");
        }
    }

    @Override
    public ResponseResult delete(List<Integer> ids) {
        int res = cartMapper.deleteBatchIds(ids);
        if(res >0){
            return ResponseResult.okResult("删除成功");
        }else
            return ResponseResult.failResult("删除失败");


    }
}
