package com.mobiusp.backstage.interceptor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mobiusp.backstage.GlobalExceptionAdvice;
import com.mobiusp.backstage.pojo.Result;
import com.mobiusp.backstage.utils.JWTUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private static final Set<String> allowOrigin = new HashSet<>();

    public TokenInterceptor () {
        allowOrigin.add("https://mobiusp.cc:3939");
        allowOrigin.add("https://bs.mobiusp.cc");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;
        response.setHeader("Access-Control-Allow-Credentials","true");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie i : cookies) {
                if (Objects.equals(i.getName(), "mobiuspT")) {
                    if (JWTUtil.checkToken(i.getValue())) {
                        result = true;
                    }
                }
            }
        }
        if (!result) {
            if (allowOrigin.contains(request.getHeader("Origin"))) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            }
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().append(JSON.toJSONString(new Result(-1,"Not logged in.",null)));
            if (! Objects.equals(request.getMethod(), "OPTIONS")) logger.info(request.getHeader("Origin") + " has no token or token error.");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
