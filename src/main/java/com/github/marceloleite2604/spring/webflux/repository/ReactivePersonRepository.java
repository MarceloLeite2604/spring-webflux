package com.github.marceloleite2604.spring.webflux.repository;

import com.github.marceloleite2604.spring.webflux.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, UUID> {
}

