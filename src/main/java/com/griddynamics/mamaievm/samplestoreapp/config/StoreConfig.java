package com.griddynamics.mamaievm.samplestoreapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("store")
@Configuration
@Getter
@Setter
public class StoreConfig {
    
    private Integer inventoryMin;
    private Integer inventoryMax;
    
}
