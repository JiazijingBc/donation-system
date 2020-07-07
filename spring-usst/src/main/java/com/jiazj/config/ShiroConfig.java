package com.jiazj.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro内置的过滤器
        /*
            anon:无需认证就可以访问
            authc；必须认证了才能访问
            user： 必须拥有 记住我 功能才能访问
            perms： 拥有对某个资源的权限才能访问
            role： 拥有某个角色权限才能访问
         */
        //拦截
        Map<String,String > filterMap = new LinkedHashMap<>();
        //授权
//        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/","anon");
        filterMap.put("/login.html","anon");
        filterMap.put("/register","anon");
        filterMap.put("/toRegister","anon");
        filterMap.put("/toOut","anon");
        filterMap.put("/login","anon");
        filterMap.put("/tologin","anon");
        filterMap.put("/toindex","anon");
        filterMap.put("/toDonation","authc");
        filterMap.put("/donation","authc");
        filterMap.put("/allDonation","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求
        bean.setLoginUrl("/tologin");
        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    //DefaultWebSecurityManager  第二步
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);


        return securityManager;
    }


    //创建 realm 对象,需要自定义    第一步
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect：用来整合shiro和thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
