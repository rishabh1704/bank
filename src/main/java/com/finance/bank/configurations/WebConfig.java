package com.finance.bank.configurations;

import com.finance.bank.interceptors.EmployeeAuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EmployeeAuthorizationInterceptor()).addPathPatterns("/employees/**").addPathPatterns("/customers/**");
    }
}
