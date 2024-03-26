package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// 拦截器：判断是否登录账号，如果没有登录则带有注解LoginRequired的方法访问不了
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    // 获取当前用户信息【没有登录则没有】
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在controller之前拦截获取到的方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 从方法注解上取需要的注解
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            // 没有登录，则拒绝后续请求return false
            // 强制跳转登录页面
            if (loginRequired != null && hostHolder.getUser() == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}



