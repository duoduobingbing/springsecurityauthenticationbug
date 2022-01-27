package de.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(
                        eh -> eh
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .authorizeRequests(ar ->
                        ar
                                .antMatchers("/public/**").permitAll()
                                .antMatchers("/", "/index", "/error").permitAll()
                                .anyRequest().authenticated()
                )
                .apply(new CustomAuthenticationConfigurer());


    }

}
