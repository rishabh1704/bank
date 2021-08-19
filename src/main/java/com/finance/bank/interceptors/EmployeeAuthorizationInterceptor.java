package com.finance.bank.interceptors;

import com.finance.bank.dto.LoggingDTO;
import com.finance.bank.logging.LoggingContext;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class EmployeeAuthorizationInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger("LOGGING");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoggingDTO loggingDTO = new LoggingDTO();
        LoggingContext.setLoggingInfo(loggingDTO);
//        logger is now dto initialized.

        LoggingContext.append("request", request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LoggingContext.append("statusCode", Integer.toString(response.getStatus()));
        LOGGER.info(LoggingContext.getLoggingInfo().toString());

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
