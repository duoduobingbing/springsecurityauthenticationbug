
package de.example.demo;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Authentication availableAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (availableAuthentication == null) {
            if (request.getParameter("user") != null) {
                
                String userid = Objects.toString(request.getParameter("user"));
                
                User user = new User(userid, "SOMEPWD", List.of());
                
                CustomAuthenticationToken token = new CustomAuthenticationToken(user);
                
                SecurityContextHolder.getContext().setAuthentication(token);
                
            }
        }
        
        filterChain.doFilter(request, response);
    }

}
