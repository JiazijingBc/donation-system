package com.jiazj.controller;

import com.jiazj.pojo.User;
import com.jiazj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;


import static java.util.Objects.hash;

@Controller
public class MyController {
    @Autowired
    private UserService userService;
    //去登录页
    @RequestMapping({"/tologin"})
    public String tologin(){
        return "redirect:/login.html";
    }
    //登录操作
    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpSession session){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装当前用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //执行登录方法，若无异常就ok
        try{
            subject.login(token);
            session.setAttribute("username",token.getUsername());
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
    //注销操作
    @RequestMapping("/toOut")
    public String toout(HttpServletRequest request, HttpServletResponse response , HttpSession session) throws IOException {
        session.removeAttribute("username");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.html";
    }
    //去首页
    @RequestMapping("/toindex")
    public String toindex(){
        return "index";
    }
    //去注册页
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
    //注册操作
    @RequestMapping("/register")
    public String register(User user,Model model){

        if (userService.queryUserByName(user.getUname())==null){
            userService.userRegService(user);
            //将用户信息放入session中
            model.addAttribute("msg","注册成功");
            return "register";
        }
        else {
            model.addAttribute("msg","用户已存在，注册失败");
            return "register";
        }
    }

    //去捐赠页
    @RequestMapping("/toDonation")
    public String toDonation(){
        return "donation";
    }
    //捐赠操作
    @RequestMapping("/donation")
    public String donation(String uname,String paymentpwd, int count,Model model){
        User user = userService.checkUserPaymentService(uname, paymentpwd);
        if(user == null){
            model.addAttribute("msg","支付密码错误");
            return "donation";
        }
        else{
            Integer donation = user.getDonation();
            if (donation==null) {
                donation=count;
            }else {
                donation+=count;
            }
            user.setDonation(donation);
            userService.userDonateService(user);
            model.addAttribute("msg","捐赠成功");
            return "donation";
        }
    }
    //查询全部捐赠信息
    @RequestMapping("/allDonation")
    public String allDonation(Model model){
        List<User> donations = userService.allDonation();
        int account = userService.account();
        model.addAttribute("donations",donations);
        model.addAttribute("account",account);
        return "allDonation";
    }
    //查询资金流向
    @RequestMapping("/allMoney")
    public String allMoney(){

        return "allMoney";
    }
    @RequestMapping("/toAbout")
    public String toAbout(){
        return "about";
    }
    @RequestMapping("/toSource")
    public String toSource(){
        return "source";
    }
    //未经授权提示
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权不得访问";
    }
}
