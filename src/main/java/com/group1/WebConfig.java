package com.group1;

import com.group1.interceptor.AdminLoginInterceptor;
import com.group1.interceptor.UserLoginInterceptor;
import com.group1.util.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {  //one thing that I have to mention, the sooner, the best

    private final LocationService locationService;

    @Autowired
    public WebConfig(LocationService locationService) {
        this.locationService = locationService;
    }

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

/**把路径切换过来，比较麻烦，统一配置，没配好啊！！！！！！！！！！！没配好啊！！！！！
 @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
 //将所有访问图片路径都映射到相应路径下
 String storeHouse=locationService.getStoreHouse();
 registry.addResourceHandler("/programstorehouse/image/**").addResourceLocations("file:"+storeHouse+"/image/");
 }
 **/
}
