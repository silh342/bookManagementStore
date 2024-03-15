package com.younes.reskillingproject.userManagement.security;


import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.error.CustomAuthenticationFailureHandler;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtAuthenticationFilter;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtGenerator;
import com.younes.reskillingproject.userManagement.security.repository.RoleRepository;
import com.younes.reskillingproject.userManagement.security.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public UserSecurityConfig(@Lazy UserRepository userRepository,
                              @Lazy RoleRepository roleRepository,
                              @Lazy AuthenticationManager authenticationManager,
                              JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

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
        return new UserServiceImpl(userRepository, roleRepository, authenticationManager, jwtGenerator);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/api/auth/**","/v3/api-docs", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/api/authors").hasAnyRole("ADMIN","USER","CUSTOMER")
                .requestMatchers("/api/authors/**").hasAnyRole("ADMIN","USER")
                .requestMatchers("/api/books/inventory/**").hasAnyRole("ADMIN","USER")
                .requestMatchers("/api/inventory/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/books", "/api/books/**")
                .hasAnyRole("ADMIN","USER","CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT , "/api/books").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                );
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF) to enable when creating front end
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}