package com.loveloveshot;

import com.loveloveshot.configuration.properties.AppProperties;
import com.loveloveshot.configuration.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class LoveloveshotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoveloveshotApplication.class, args);
    }

}
