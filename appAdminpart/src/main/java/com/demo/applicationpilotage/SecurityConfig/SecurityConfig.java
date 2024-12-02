package com.demo.applicationpilotage.SecurityConfig;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Désactive CSRF pour faciliter les tests
            .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/auth/users/employes").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/auth/users/managers").permitAll()
                .requestMatchers(HttpMethod.GET,"/admin/pending-users").permitAll()
                .requestMatchers(HttpMethod.PUT,"/admin/enable-user/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/admin/enable-user/**").permitAll() // Added rule
                .requestMatchers(HttpMethod.GET, "/admin/users/managers").permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/users/employes").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/profile/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/admin/users").permitAll() 
                .anyRequest().permitAll(); 
        // Permet l'accès à toutes les autres routes
        return http.build();
    }
}
