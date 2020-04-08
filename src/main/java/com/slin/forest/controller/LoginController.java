package com.slin.forest.controller;

import com.slin.forest.model.UserBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author yangsonglin
 *         Created on 2017/11/9
 */
@Controller
public class LoginController {


    @RequestMapping(value = {"/","/index","/login"})
    public String login(){
        return "login/login";
    }


    @RequestMapping("/main")
    public String auMain(Model model){
        model.addAttribute("title","标题");
        model.addAttribute("content","内容");
        model.addAttribute("extraInfo","你是admin");

        return "main/main";
    }

    @RequestMapping("/loginUser")
    public String loginUser(String username,String password,HttpSession session) {

        //授权认证
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
            //获得用户对象
            UserBean user=(UserBean) subject.getPrincipal();
            //存入session
            session.setAttribute("user", user);
            return "redirect:/main";
        } catch(Exception e) {
            return "login/login";//返回登录页面
        }

    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login/login";
    }
}
