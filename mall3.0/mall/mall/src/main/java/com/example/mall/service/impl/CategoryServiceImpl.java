package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Category;
import com.example.mall.dao.CategoryMapper;
import com.example.mall.domain.vo.CategoryVo;
import com.example.mall.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ResponseResult delete(List<Integer> ids) {
        int res = categoryMapper.deleteBatchIds(ids);
        if(res >0){
            return ResponseResult.okResult("删除成功");
        }else
            return ResponseResult.failResult("删除失败");

    }

    @Override
    public ResponseResult updatecategory(Category category) {
        int res = categoryMapper.updateById(category);
        if(res ==1){
            return ResponseResult.okResult("修改商品成功");
        }
        else{
            return ResponseResult.failResult("修改商品失败");
        }
    }

    @Override
    public ResponseResult insertcategory(Category category) {
        int res = categoryMapper.insert(category);
        if (res == 0){
            return  ResponseResult.failResult("插入商品失败");
        }else {
            return ResponseResult.okResult("插入商品成功");
        }
    }

    @Override
    public ResponseResult getcategory(Integer id) {
        Category category = categoryMapper.selectById(id);
        if (category ==null){
            return ResponseResult.failResult("找不到对应的商品");
        }else {
            CategoryVo categoryVO = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVO);
            return ResponseResult.okResult(categoryVO);
        }
    }

    @Override
    public ResponseResult getcategoryList(Integer pageNum, Integer pageSize) {
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        List<Category> categories = categoryMapper.selectPage(categoryPage, null).getRecords();
        List<CategoryVo> bannerVOs = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(bannerVOs);
    }
}
