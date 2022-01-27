
package de.example.demo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomAuthenticationFilter extends OncePerRequestFilter {


    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getParameter("user") != null) {

            String userid = Objects.toString(request.getParameter("user"));

            User user = new User(userid, "SOMEPWD", List.of());

            CustomAuthenticationToken token = new CustomAuthenticationToken(user);
            try {
                Authentication authenticationResult = this.authenticationManager.authenticate(token);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationResult);
                SecurityContextHolder.setContext(securityContext);

                filterChain.doFilter(request, response);
            } catch (AuthenticationException authenticationException) {
                this.authenticationEntryPoint.commence(request, response, authenticationException);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
