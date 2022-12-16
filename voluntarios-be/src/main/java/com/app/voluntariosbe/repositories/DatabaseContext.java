package com.app.voluntariosbe.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DatabaseContext {

    @Value ("${spring.data.mongodb.database}")
    private String databaseName;

    @Value ("${spring.data.mongodb.port}")
    private int port;

    @Value ("${spring.data.mongodb.host}")
    private String host;

    @Value ("${spring.data.mongodb.username}")
    private String username;

    @Value ("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://mongo:mongo@localhost:27017/voluntarios");
    }
}