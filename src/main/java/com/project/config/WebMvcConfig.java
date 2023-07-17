package com.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Value("${uploadPath}")
    String uploadPath;

    String saveBoardPath = "file:///C:/project_resources/board/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/furniture/**")
                .addResourceLocations(uploadPath);

        registry.addResourceHandler("/board/**")
                .addResourceLocations(saveBoardPath);
    }
}
