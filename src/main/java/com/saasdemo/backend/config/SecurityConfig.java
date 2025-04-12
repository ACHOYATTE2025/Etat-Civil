package com.saasdemo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
     return httpSecurity
     .csrf(AbstractHttpConfigurer::disable)
     .authorizeHttpRequests(authorize->
     authorize
                              .requestMatchers(HttpMethod.POST,"/signup").permitAll()
                              .anyRequest().authenticated())
                              
                              
                              .build();
  }

  
  
    
}