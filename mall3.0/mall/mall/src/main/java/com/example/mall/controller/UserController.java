package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;
import com.example.mall.domain.vo.UserVo;
import com.example.mall.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lcy
 * @since 2024-07-20
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public ResponseResult login(String phone, String password, String verifyCode, HttpSession session){
       if (phone.isEmpty()||password.isEmpty()){
            return ResponseResult.failResult("手机号或密码不能为空");
        }
        if (verifyCode.isEmpty()) {
            return ResponseResult.failResult("验证码不能为空");
        }
        if (!phone.matches("^\\d{11}$")){
            return ResponseResult.failResult("请输入正确的手机号");
        }

        String captchaCode = session.getAttribute("verifyCode").toString();
        if(!verifyCode.toLowerCase().equals(captchaCode)){
            return ResponseResult.failResult("验证码错误");
        }
        User user = userService.login(phone,password);
        if (user != null){
            //用户登录以后，往session里面传id
            session.setAttribute("userId",user.getId());
            /*使用UserVo封装User*/
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return ResponseResult.okResult(user);
        }
        else {
            return  ResponseResult.failResult("登录失败");
        }

    }

    @PostMapping("/reg")
    public  ResponseResult register(String phone,String password,String password2,String verifyCode,HttpSession session){
        if (phone.isEmpty()||password.isEmpty()){
            return ResponseResult.failResult("手机号或密码不能为空");
        }
        if (password2.isEmpty()) {
            return ResponseResult.failResult("请再次确认密码");
        }
        if (verifyCode.isEmpty()) {
            return ResponseResult.failResult("验证码不能为空");
        }
        if (!phone.matches("^\\d{11}$")){
            return ResponseResult.failResult("请输入正确的手机号");
        }
        if (!password.equals(password2)){
            return ResponseResult.failResult("两次输入密码不一致");
        }
        String captchaCode = session.getAttribute("verifyCode").toString();
        if(!verifyCode.toLowerCase().equals(captchaCode)){
            return ResponseResult.failResult("验证码错误");
        }
        if (userService.userExist(phone)){
            return ResponseResult.failResult("账号已存在");
        }
        int res =userService.register(phone,password);
        if (res==1){
          return   ResponseResult.okResult("注册成功");
        }
        else {
          return   ResponseResult.failResult("注册失败");
        }
    }
    @GetMapping("/logout")
    public ResponseResult logout(HttpSession session){
        session.removeAttribute("userId");
        return ResponseResult.okResult("退出成功");
    }
    @GetMapping("/isLogin")
    public ResponseResult islogin(HttpSession session,User user){
        Object userId = session.getAttribute("userId");
        if (userId == null){
            return ResponseResult.failResult("当前未登录");
        }else {
            return this.getUser(Integer.valueOf(userId.toString()));
        }
    }
    @GetMapping("/admin/list")
    public  ResponseResult getUserList(@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5") Integer pageSize){
        return userService.getUserList(pageNum,pageSize);
    }

    @GetMapping("/admin/{id}")
    public ResponseResult getUser(@PathVariable("id") Integer id){
      return  userService.getUser(id);
    }
    @PostMapping("/admin/update")
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @PostMapping("/admin/delete")
    private ResponseResult deleteUser(@RequestBody List<Integer> ids){
        return userService.deleteUser(ids);
    }

}
