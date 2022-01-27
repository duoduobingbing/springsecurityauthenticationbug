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
