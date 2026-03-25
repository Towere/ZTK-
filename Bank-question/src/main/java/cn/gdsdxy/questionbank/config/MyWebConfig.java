package cn.gdsdxy.questionbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
//@Profile("prod")
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirPath =System.getProperty("user.dir")+"\\file\\";
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+dirPath);



    }
}
