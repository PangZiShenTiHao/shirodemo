package com.hxch.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){

        // 1.获取subject 对象
        Subject subject = SecurityUtils.getSubject();
        // 2.判断subject 是否登录了
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);

            try{
                subject.login(token);
            }catch (AuthenticationException ae){
                System.out.println("登录失败："+ae.getMessage());
            }

        }
        return "redirect:/list.jsp";
    }
}
