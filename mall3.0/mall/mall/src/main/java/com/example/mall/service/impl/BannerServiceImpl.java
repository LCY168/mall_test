package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.dao.BannerMapper;
import com.example.mall.domain.vo.BannerVo;
import com.example.mall.service.IBannerService;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
    public ResponseResult getBannerList(Integer pageNum, Integer pageSize) {
        Page<Banner> bannerPage = new Page<>(pageNum,pageSize);
        List<Banner> banners = bannerMapper.selectPage(bannerPage, null).getRecords();
        List<BannerVo> bannerVos = BeanCopyUtils.copyBeanList(banners, BannerVo.class);
        return ResponseResult.okResult(bannerVos);
    }

    @Override
    public ResponseResult getBanner(Integer id) {
        Banner banner = bannerMapper.selectById(id);
        if (banner ==null){
            return ResponseResult.failResult("找不到对应的图片");
        }else {
            BannerVo bannerVO =new BannerVo();
            BeanUtils.copyProperties(banner,bannerVO);
            return ResponseResult.okResult(bannerVO);
        }

    }


    @Override
    public ResponseResult insertBanner(Banner banner) {
        int res = bannerMapper.insert(banner);
        if (res == 0){
            return  ResponseResult.failResult("插入失败");
        }else {
            return ResponseResult.okResult("插入成功");
        }
    }

    @Override
    public ResponseResult updateBanner(Banner banner) {
        int res =bannerMapper.updateById(banner);
        if(res ==1){
            return ResponseResult.okResult("修改成功");
        }
        else{
            return ResponseResult.failResult("修改失败");
        }
    }

    @Override
    public ResponseResult delete(List<Integer> ids) {
        int res = bannerMapper.deleteBatchIds(ids);
        if(res >0){
            return ResponseResult.okResult("删除成功");
        }else
            return ResponseResult.failResult("删除失败");


    }
}
