package com.example.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */

public interface IUserService extends IService<User> {

    User login(String phone, String password);

    boolean userExist(String phone);

    int register(String phone, String password);

    ResponseResult getUserList(Integer pageNum, Integer pageSize);

    ResponseResult getUser(Integer id);


    ResponseResult updateUser(User user);

    ResponseResult deleteUser(List<Integer> ids);
}
