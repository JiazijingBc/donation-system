package com.jiazj.mapper;

import com.jiazj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author jiazijing
 */
@Repository
@Mapper
public interface UserMapper {
    /*
    查询根据用户名密码查询用户信息
        uname 用户名
        pwd   密码
        return   返回查询到的用户信息
     */
    //查询全部用户
    List<User> allUser();
    //登录
    User checkUserPaymentDao(@Param("uname") String uname, @Param("paymentpwd") String paymentpwd);

    //用户注册
    int userRegDao(User u);
    //用户捐献
    int userDonateDao(User user);

    User queryUserByName(String uname);

    List<User> allDonation();

    int account();
}
