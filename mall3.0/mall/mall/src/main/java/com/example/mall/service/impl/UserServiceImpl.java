package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.vo.UserVo;
import com.example.mall.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.util.BeanCopyUtils;
import com.example.mall.util.MD5Util;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
   @Autowired
   UserMapper userMapper;

    @Override
    public User login( String phone,String password){
        String passwordMd5 = MD5Util.MD5Encode(password ,"UTF-8");
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
  //     queryWrapper.eq("phone",phone)
 //              .eq("password",passwordMd5);
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone)
                .eq(User::getPassword,passwordMd5);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean userExist(String phone) {
       LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        return userMapper.exists(queryWrapper);
    }

    @Override
    public int register(String phone, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(MD5Util.MD5Encode(password,"UTF-8"));
        user.setNickName("用户"+phone);
        return  userMapper.insert(user);
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize) {
        Page<User> userPage = new Page<>(pageNum,pageSize);
        List<User> users = userMapper.selectPage(userPage, null).getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);

        return ResponseResult.okResult(userVos);
    }

    @Override
    public ResponseResult getUser(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null){
            return  ResponseResult.failResult();
        }else {
            UserVo userVO = new UserVo();
            BeanUtils.copyProperties(user,userVO);
            return ResponseResult.okResult(userVO);

        }
    }

    @Override
    public ResponseResult updateUser(User user) {
        int res = userMapper.updateById(user);
        if(res ==1){
            return ResponseResult.okResult("修改成功");
        }
        else{
            return ResponseResult.failResult("修改失败");
        }

    }

    @Override
    public ResponseResult deleteUser(List<Integer> ids) {
        int res = userMapper.deleteBatchIds(ids);
        if(res >0){
            return ResponseResult.okResult("删除成功");
        }else
            return ResponseResult.failResult("删除失败");

    }
}
