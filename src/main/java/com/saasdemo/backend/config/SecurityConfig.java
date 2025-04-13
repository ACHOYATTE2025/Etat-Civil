package com.saasdemo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.saasdemo.backend.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

private final JwtAuthenticationFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
     return httpSecurity
     .csrf(AbstractHttpConfigurer::disable)
     .authorizeHttpRequests(authorize->
     authorize
                              .requestMatchers(HttpMethod.POST,"/signup").permitAll()
                              .anyRequest().authenticated())
                              .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                              
                              .build();
  }

  
  
    
}