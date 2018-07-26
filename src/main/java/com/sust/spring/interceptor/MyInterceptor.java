package com.sust.spring.interceptor;

import com.sust.spring.domain.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        //String uri = requestURI.substring(requestURI.lastIndexOf("/"));
        if (requestURI.startsWith("/user/login") || requestURI.startsWith("/user/dologin")){
            return true;
        }else{
            Object o = session.getAttribute("userSession");
            //String userName = ((User) o).getUserName();
            if (o == null){
                response.sendRedirect(request.getContextPath()+"/WEB-INF/jsp/error.jsp");
                return false;
            }
            return true;
        }

    }
}
