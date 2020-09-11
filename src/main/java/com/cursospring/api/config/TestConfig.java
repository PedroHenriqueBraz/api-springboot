package com.cursospring.api.config;

import com.cursospring.api.service.DbService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {
    private final DbService dbService;

    public TestConfig(DbService dbService) {
        this.dbService = dbService;
    }
    @Bean
    public boolean instatiateDb() throws ParseException {
        dbService.instatiateTestDb();
        return true;
    }
}
