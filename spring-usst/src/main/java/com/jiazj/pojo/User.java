package com.jiazj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;
    private String uname;
    private String pwd;
    private String paymentpwd;
    private Integer donation;
    private String perms;


}
