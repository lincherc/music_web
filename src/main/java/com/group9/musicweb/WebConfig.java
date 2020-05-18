package com.group9.musicweb;

import com.group9.musicweb.interceptor.AdminLoginInterceptor;
import com.group9.musicweb.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {  //one thing that I have to mention, the sooner, the best
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/static/**")
                // .excludePathPatterns("/static/adminstatic/**")
                .excludePathPatterns("/user/index")
                .excludePathPatterns("/user/register");
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
    }
}
