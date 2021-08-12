package com.finance.bank.interceptors;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class EmployeeAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("preHandle");
//        Cookie[] cookies = request.getCookies();
////        read body of request
//        Long empid;
//        for (Cookie cookie : cookies) {
//            if (cookie.getName() == "employeeId") {
//                log.info(cookie.getValue() == empid.toString());
//            }
//        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
