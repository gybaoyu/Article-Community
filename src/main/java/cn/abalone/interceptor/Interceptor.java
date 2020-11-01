package cn.abalone.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.abalone.entity.Prop.ackey;

/**
 * Create by Abalone
 * CreateTime: 2020/10/5 20:48
 */

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return ackey().equals(request.getParameter("ackey"));
    }
}
