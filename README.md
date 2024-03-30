This setup incorrectly did not clear the AuthenticationContext and thus caused unexpected behavior after an upgrade.

If you want to have a look at how to clear and set the Context correctly, have a look at any implementation of AuthenticationFilter
like: https://github.com/spring-attic/spring-security-oauth/blob/main/spring-security-oauth2/src/main/java/org/springframework/security/oauth2/provider/authentication/OAuth2AuthenticationProcessingFilter.java


# Spring Security 2.6+ Authentication Bug

Throwing an AuthenticationException in a custom AuthenticationProvider during authentication when
using a defined error page causes Spring to deliver assets empty with a 401 status code even though they are configured
with `.antMatchers("/public/**").permitAll()`.

## How to reproduce

* Build with Maven and Java 17.
* Run
* Goto http://localhost:8080
* Click on the link
* A white page (instead of a yellow one) is displayed, because the CSS is returned with 401

## Workarounds
1. Dowgrade to Spring Boot 2.5.8
2. Add the assets explictly to the ignoringAntMatchers under `webSecurity.ignoring().antMatchers("/public/**");`
