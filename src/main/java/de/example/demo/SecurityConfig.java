package de.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    PasswordEncoder passwordEncoder;
    CustomAuthenticationEntryPoint entryPoint;
    CustomAuthenticationFilter filter;
    CustomAuthenticationProvider provider;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        
        entryPoint = new CustomAuthenticationEntryPoint();
        filter = new CustomAuthenticationFilter();
        provider = new CustomAuthenticationProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(
                        eh -> eh
                                .authenticationEntryPoint(entryPoint)
                )
                .authorizeRequests(ar ->
                        ar
                                .antMatchers("/public/**").permitAll()
                                .antMatchers("/", "/index", "/error").permitAll()
                                .anyRequest().authenticated()
                );
        
        http.addFilterAfter(filter, LogoutFilter.class);
        

    }

}
