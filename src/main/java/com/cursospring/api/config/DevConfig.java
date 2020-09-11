package com.cursospring.api.config;

import com.cursospring.api.service.DbService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {
    private final DbService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    public DevConfig(DbService dbService) {
        this.dbService = dbService;
    }
    @Bean
    public boolean instatiateDb() throws ParseException {
//        if(!"create".equals(strategy)){
//            return false;
//        }
        dbService.instatiateTestDb();
        return true;
    }
}
