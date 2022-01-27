package de.example.demo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class CustomAuthenticationConfigurer extends AbstractHttpConfigurer<CustomAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) throws Exception {
        http.authenticationProvider(new CustomAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        AuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager, authenticationEntryPoint);
        http.addFilterAfter(authenticationFilter, BasicAuthenticationFilter.class);
    }
}
