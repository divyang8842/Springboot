package com.Security;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class RequestIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);

        String uri = request.getRequestURI();
        if(uri.equalsIgnoreCase("/user/add") || uri.equalsIgnoreCase("/user/login")){
            System.out.println("URI is "+uri);
            return true;
        }

        if(session.getAttribute("id")!=null) {
            String userid = String.valueOf(session.getAttribute("id"));
            if (userid == null || userid.equals("0")) {
                session.invalidate();
                return false;
            }
        }
        System.out.println("valid URI is "+uri);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}