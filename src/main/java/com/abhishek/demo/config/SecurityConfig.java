package com.abhishek.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
    {
        // used to disable csrf token
        security.csrf(customizer -> customizer.disable());

        // ensures user is authenticated before using any resource
        security.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        // provides form login feature for authentication
        security.formLogin(Customizer.withDefaults());

        // provides authentication using headers, with this line we can fire api from postman using username and password
        security.httpBasic(Customizer.withDefaults());

        // makes session policy stateless
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // builds and returns security filter chain with above customized filters
        return security.build();
    }


    @Bean
    public UserDetailsService userDetails()
    {
        UserDetails user1 = User.withDefaultPasswordEncoder().username("aniket").password("aniket@1234").roles("USER").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("harsh").password("harsh@1234").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

}
