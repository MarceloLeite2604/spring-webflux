package com.github.marceloleite2604.spring.webflux.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class ReactiveMongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient createMongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "application-db";
    }
}