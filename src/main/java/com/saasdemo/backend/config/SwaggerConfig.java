package com.saasdemo.backend.config;

import org.springframework.context.annotation.Configuration;

import com.saasdemo.backend.dto.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


//configuration de swagger 
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI CustomOpenAi(){
    return new OpenAPI()
        .info(new Info()
        .title("API Gestion des États Civils")
        .version("1.0")
        .description("Documentation interactive de l'API de gestion des naissances, mariages, décès, etc."));

  }

   
    
}