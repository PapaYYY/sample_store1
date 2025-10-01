package com.griddynamics.mamaievm.samplestoreapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//to be able to test localy without security headers passed:
//# @no-cookie-jar
//GET http://localhost:8080/api/v1/products
//Cookie: JSESSIONID=8D313D3A1A4ED3F02DC69427E648BB48
@Configuration
@Profile("no_auth")
public class ApplicationNoSecurity {
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/**"));
    }
}
