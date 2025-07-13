package com.miempresa.serviceproduct.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorize ->
                                    authorize
                                            // Permitir GET en /category/**
                                            .requestMatchers(HttpMethod.GET, "/product/**").permitAll()

                                            // Requiere autenticación para otros métodos en /category/**
                                            .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("manageProduct")
                                            .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("manageProduct")
                                            .requestMatchers(HttpMethod.PATCH, "/product/**").hasAuthority("manageProduct")
                                            .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("manageProduct")

                                            .anyRequest().authenticated()
                )
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        //Al pasar a produccion cambiar por el dominio de la web (es el mismo api-gateway)
        //configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
