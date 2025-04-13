package com.saasdemo.backend.config;


import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


//Initier l'initialisation à la base données
@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/GestionEtatCivil")
                .username("postgres")
                .password("dreamcast1985")
                .driverClassName("org.postgresql.Driver")
                
               
                .build();
    }
}
