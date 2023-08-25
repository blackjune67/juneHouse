package com.junehouse;

import com.junehouse.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class JuneHouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuneHouseApplication.class, args);
    }

}
