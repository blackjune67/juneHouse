package com.junehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "environments")
public class AppConfig {
    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }

    private Map<String, String> local;
}
