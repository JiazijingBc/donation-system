package com.jiazj.service;

import com.jiazj.mapper.UserMapper;
import com.jiazj.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;



    @Override
    public List<User> allUser() {
        return userMapper.allUser();
    }


    @Override
    public User checkUserPaymentService(String uname, String paymentpwd) {
        return userMapper.checkUserPaymentDao(uname,paymentpwd);
    }

    @Override
    public int userRegService(User u) {
        return userMapper.userRegDao(u);
    }

    @Override
    public int userDonateService(User user) {
        return userMapper.userDonateDao(user);
    }

    @Override
    public List<User> allDonation() {
        return userMapper.allDonation();
    }
    @Override
    public User queryUserByName(String uname) {
        return userMapper.queryUserByName(uname);
    }

    @Override
    public int account() {
        return userMapper.account();
    }
}
