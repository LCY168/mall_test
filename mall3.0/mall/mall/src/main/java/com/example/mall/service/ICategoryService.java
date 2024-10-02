package com.example.mall.service;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.Category;
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
public interface ICategoryService extends IService<Category> {


    ResponseResult delete(List<Integer> ids);

    ResponseResult updatecategory(Category category);

    ResponseResult insertcategory(Category category);

    ResponseResult getcategory(Integer id);

    ResponseResult getcategoryList(Integer pageNum, Integer pageSize);
}
