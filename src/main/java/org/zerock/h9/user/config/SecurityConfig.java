package org.zerock.h9.user.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.zerock.h9.user.security.CustomAccessDeniedHandler;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    {
        log.info("===SecurityConfig===");
        log.info("===SecurityConfig===");
        log.info("===SecurityConfig===");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        log.info("===configure===");
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/USER").hasRole("USER")
//                .antMatchers("/sample/ADMIN").hasRole("ADMIN");
//        http.formLogin();
//        http.exceptionHandling().accessDeniedHandler(accessDenieHandler());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user00")
                .password(passwordEncoder().encode("1111"))
                .authorities("ROLE_USER");
    }
}
