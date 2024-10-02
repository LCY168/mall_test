package com.example.mall.service;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Product;
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
public interface IProductService extends IService<Product> {

    

    ResponseResult getProduct(Integer id);

    ResponseResult insertProduct(Product product);

    ResponseResult updateProduct(Product product);

    ResponseResult delete(List<Integer> ids);

    ResponseResult getProductList(String name, Integer categoryId, Integer pageNum, Integer pageSize);


}
