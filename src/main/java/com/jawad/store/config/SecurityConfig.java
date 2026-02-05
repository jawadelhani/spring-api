package com.jawad.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //make it stateless sessions(token-based)
        //disable CSRF (no need improve performance)
        //authorize some routes (no need to auth)
        http
            .sessionManagement(c->
                    c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)    //equivalent to c->c.disable()
            .authorizeHttpRequests(c->c.
                    requestMatchers("/carts/**").permitAll()           //allowed req
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                    .anyRequest().authenticated()
                    );
        return http.build();
    }
}

