package com.loveloveshot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:static/properties/image.properties")
public class WebConfig implements WebMvcConfigurer {

    @Value("${resource.handler}")
    private String resourceHandler;

    @Value("${resource.location}")
    private String resourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourceHandler)
                .addResourceLocations(resourceLocation);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 모든 uri에 대해 http://localhost:18080, http://localhost:8180 도메인은 접근을 허용한다.
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://loveloveshot.com", "http://192.168.0.12:3000")
//                .allowedMethods("GET", "POST")
//                .allowCredentials(true);
//    }
}
