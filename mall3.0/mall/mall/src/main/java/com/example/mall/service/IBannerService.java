package com.example.mall.service;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */
public interface IBannerService extends IService<Banner> {

    ResponseResult getBannerList(Integer pageNum, Integer pageSize);

    ResponseResult getBanner(Integer id);

    ResponseResult insertBanner(Banner banner);

    ResponseResult updateBanner(Banner banner);


    ResponseResult delete(List<Integer> ids);
}
