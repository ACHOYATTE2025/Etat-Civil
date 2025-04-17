package com.saasdemo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.saasdemo.backend.security.JwtAuthenticationFilter;
import com.saasdemo.backend.security.SubscriptionGuardFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
private PasswordEncoder passwordEncoder;
private final JwtAuthenticationFilter jwtFilter;
private final SubscriptionGuardFilter subscriptionGuardFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
     return httpSecurity
     .csrf(AbstractHttpConfigurer::disable)
     .authorizeHttpRequests(authorize->
     authorize
                              .requestMatchers(HttpMethod.POST,"/signup").permitAll()
                              .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()//permettre l'affichage de swagger
                              .requestMatchers(HttpMethod.POST,"/reactiveCompteAdmin").permitAll()
                              .requestMatchers(HttpMethod.POST,"/loginAdmin").permitAll()
                              .anyRequest().authenticated())
                              .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                              .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                              .addFilterAfter(subscriptionGuardFilter, JwtAuthenticationFilter.class)
                              .build(); }
   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

  
  
    
}