package com.example.mall.config;

import com.example.mall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
   @Override
    public void addInterceptors(InterceptorRegistry registry) {
     registry.addInterceptor(new LoginInterceptor())
         .addPathPatterns("/*/admin/**");

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\uploade\\");
    }
}
