
package de.example.demo;

import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (Objects.equals(authentication.getName(), "1")) {
            authentication.setAuthenticated(true);
            return authentication;
        } else {
            throw new UsernameNotFoundException("Unknown userid <" + authentication.getName() + ">. Was not <1>.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
