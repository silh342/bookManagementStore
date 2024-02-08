package com.younes.reskillingproject.userManagement.security;


import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.error.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        http.authorizeHttpRequests(configurer -> configurer
//                .requestMatchers("/api/user").permitAll()
//                .requestMatchers("/api/role").hasRole("ADMIN")
//                .requestMatchers("/api/authors").hasAnyRole("ADMIN","USER")
//                .requestMatchers("/api/authors/**").hasAnyRole("ADMIN","USER")
//                .requestMatchers("/api/books/inventory/**").hasAnyRole("ADMIN","USER")
//                .requestMatchers("/api/inventory/**").hasAnyRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/books").hasAnyRole("ADMIN","USER","CUSTOMER")
//                .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("ADMIN","USER","CUSTOMER")
//                .requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("USER","ADMIN")
//                .requestMatchers(HttpMethod.PUT , "/api/books").hasAnyRole("USER","ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN"));
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF) to enable when creating front end
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}