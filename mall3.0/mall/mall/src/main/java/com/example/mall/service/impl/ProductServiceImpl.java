package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.dao.CategoryMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Product;
import com.example.mall.domain.vo.ProductVo;
import com.example.mall.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {


    @Autowired
    ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
   public ResponseResult getProductList(String name, Integer categoryId, Integer pageNum, Integer pageSize) {
       Page<Product> productPage = new Page<>(pageNum,pageSize);
       System.out.println(pageNum);
       System.out.println(pageSize);
       LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.like(name!=null,Product::getTitle,name)
               .eq(categoryId!=null,Product::getCategoryId,categoryId);
       List<Product> products = productMapper.selectPage(productPage,queryWrapper).getRecords();
       if(name !=null&&products.isEmpty()){
           return ResponseResult.failResult("查询不到该产品");
       }
       System.out.println(products);
        List<ProductVo> productVOs = BeanCopyUtils.copyBeanList(products, ProductVo.class);
       for (ProductVo productVo:productVOs){
            productVo.setCategoryName(categoryMapper.selectById(productVo.getCategoryId()).getName());
        }
        return ResponseResult.okResult(productVOs);
    }

    @Override
    public ResponseResult getProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if (product ==null){
            return ResponseResult.failResult("找不到对应的图片");
        }else {
            ProductVo productVO =new ProductVo();
            BeanUtils.copyProperties(product,productVO);
            productVO.setCategoryName(categoryMapper.selectById(productVO.getCategoryId()).getName());
            return ResponseResult.okResult(productVO);
        }

    }


    @Override
    public ResponseResult insertProduct(Product product) {
       if(product.getCode()  == null){
           return ResponseResult.failResult("商品编码不能为空");
       }
        Product prod = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getCode,product.getCode()));
        if(prod == null){
            int res = productMapper.insert(product);
            if (res == 0){
                return  ResponseResult.failResult("插入商品失败");
            }else {
                return ResponseResult.okResult("插入商品成功");
            }
        }else {
            return ResponseResult.failResult(501,"商品编码已经存在,插入失败");
        }

    }

    @Override
    public ResponseResult updateProduct(Product product) {
        int res =productMapper.updateById(product);
        if(res ==1){
            return ResponseResult.okResult("修改成功");
        }
        else{
            return ResponseResult.failResult("修改失败");
        }
    }

    @Override
    public ResponseResult delete(List<Integer> ids) {
        int res = productMapper.deleteBatchIds(ids);
        if(res >0){
            return ResponseResult.okResult("删除成功");
        }else
            return ResponseResult.failResult("删除失败");


    }
}
