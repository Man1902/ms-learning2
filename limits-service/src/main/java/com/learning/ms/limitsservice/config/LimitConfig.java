package com.learning.ms.limitsservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("limits-service")
public class LimitConfig {
    private int minimum;
    private int maximum;
}
