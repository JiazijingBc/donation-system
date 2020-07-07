package com.jiazj.service;

import com.jiazj.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    /*
    校验用户登录
     */
    List<User> allUser();

    User checkUserPaymentService(@Param("uname") String uname, @Param("paymentpwd") String paymentpwd);
    //用户注册
    int userRegService(User u);

    //捐献
    int userDonateService(User user);

    List<User> allDonation();

    User queryUserByName(String uname);

    int account();
}
