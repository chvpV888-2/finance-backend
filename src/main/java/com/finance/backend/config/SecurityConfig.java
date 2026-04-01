package com.finance.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ALLOW SWAGGER DOCUMENTATION FOR EVERYONE
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Viewers can only READ data
                        .requestMatchers(HttpMethod.GET, "/api/records/**").hasAnyRole("VIEWER", "ANALYST", "ADMIN")

                        // Analysts can see the dashboard math
                        .requestMatchers("/api/dashboard/**").hasAnyRole("ANALYST", "ADMIN")

                        // ONLY Admins can create, update, or delete records
                        .requestMatchers(HttpMethod.POST, "/api/records/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/records/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/records/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails viewer = User.withDefaultPasswordEncoder().username("viewer").password("pass").roles("VIEWER").build();
        UserDetails analyst = User.withDefaultPasswordEncoder().username("analyst").password("pass").roles("ANALYST").build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("pass").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(viewer, analyst, admin);
    }
}