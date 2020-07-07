package com.jiazj.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("username")!=null){
            return true;
        }
        if(request.getRequestURI().contains("checkin")){
            return true;
        }
        if(request.getRequestURI().contains("login")){
            return true;
        }
        if(request.getRequestURI().contains("toRegister")){
            return true;
        }
        if(request.getRequestURI().contains("register")){
            return true;
        }
        if(request.getRequestURI().contains("index")){
            return true;
        }
        request.getRequestDispatcher("/login.jsp").forward(request,response);
        return false;
    }
}
