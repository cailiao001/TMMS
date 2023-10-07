package com.example.controller;

import com.example.common.R;
import com.example.pojo.User;
import com.example.vo.UserVo;
import com.example.service.UserService;
import com.example.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/generateImgCode")
    public void generateImgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        request.getSession().setAttribute("code",code);
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        VerifyCodeUtils.outputImage(180,40,outputStream,code);
    }


    @PostMapping("/register")
    public R<String> register(@RequestBody UserVo userVo, HttpServletRequest request) throws UnsupportedEncodingException {
        try {
            String sessionCode = (String) request.getSession().getAttribute("code");
            if(!sessionCode.equals(userVo.getCode())){
                log.info("验证码匹配不成功");
                throw new RuntimeException("验证码错误!");
            }
            userService.register(userVo);
        } catch (RuntimeException e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        log.info("注册成功.......................");
        return R.success("注册成功");
    }



    @PostMapping("/login")
    public R<User> login(@RequestBody User user,HttpServletRequest request) throws UnsupportedEncodingException {
        User us = null;
        try {
            us = userService.login(user);
        } catch (RuntimeException e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        log.info("登陆成功.........................");
        request.getSession().setAttribute("user",us);
        return R.success(us);
    }


    @GetMapping("/logout")
    public R<String> logout (HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        log.info("退出登录.................................");
        return R.success("退出成功");
    }

}
