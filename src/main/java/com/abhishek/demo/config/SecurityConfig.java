package com.abhishek.demo.config;


import com.abhishek.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
    {
        // used to disable csrf token
        security.csrf(AbstractHttpConfigurer::disable);

        // ensures user is authenticated before using any resource
        security.authorizeHttpRequests(request -> request
                        .requestMatchers("signIn", "register")
                        .permitAll()
                        .anyRequest().authenticated());

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
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // Using NoOp
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

}
