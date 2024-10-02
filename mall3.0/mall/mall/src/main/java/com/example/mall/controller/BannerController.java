package com.example.mall.controller;


import com.example.mall.dao.BannerMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.service.impl.BannerServiceImpl;
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
@RequestMapping("/banner")
public class BannerController {
    @Autowired
     private BannerServiceImpl bannerService;
    @GetMapping("/list")
    public ResponseResult getBannerList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
        return bannerService.getBannerList(pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getBanner(@PathVariable Integer id){
        return bannerService.getBanner(id);

    }
    @PostMapping("admin/insert")
    public ResponseResult insertBanner(@RequestBody Banner banner){
        return bannerService.insertBanner(banner);

    }
    @PostMapping("/admin/update")
    public ResponseResult updateBanner(@RequestBody Banner banner){
        return  bannerService.updateBanner(banner);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteBanner(@RequestBody List<Integer> ids){
        return  bannerService.delete(ids);
    }

}
