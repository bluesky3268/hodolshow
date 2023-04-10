package com.hyunbennylog.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Base64;

//@Configuration
@ConfigurationProperties(prefix = "hyunbennylog")
public class AppConfig {

    private byte[] jwt_key;

    public void setJwt_key(String jwt_key) {
        this.jwt_key = Base64.getDecoder().decode(jwt_key);
    }

    public byte[] getJwt_key() {
        return jwt_key;
    }
}
