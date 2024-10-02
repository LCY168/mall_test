package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.Category;
import com.example.mall.service.impl.CategoryServiceImpl;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @GetMapping("/list")
    public ResponseResult getCategoryList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
        return categoryService.getcategoryList(pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getCategory(@PathVariable Integer id){
        return categoryService.getcategory(id);

    }
    @PostMapping("admin/insert")
    public ResponseResult insertCategory(@RequestBody Category category){
        return categoryService.insertcategory(category);

    }
    @PostMapping("/admin/update")
    public ResponseResult updateCategory(@RequestBody Category category){
        return  categoryService.updatecategory(category);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteCategory(@RequestBody List<Integer> ids){
        return  categoryService.delete(ids);
    }

}
