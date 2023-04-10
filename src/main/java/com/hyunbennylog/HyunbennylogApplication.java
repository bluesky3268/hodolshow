package com.hyunbennylog;

import com.hyunbennylog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class HyunbennylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyunbennylogApplication.class, args);
    }

}
