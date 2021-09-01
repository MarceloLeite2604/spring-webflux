package com.github.marceloleite2604.spring.webflux.repository;

import com.github.marceloleite2604.spring.webflux.model.Person;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, UUID> {

    Flux<Person> findByLastName(String lastName);

    @Query("{ 'firstName': ?0, 'lastName': ?1}")
    Mono<Person> findByFirstNameAndLastName(String firstName, String lastName);

    Flux<Person> findByLastName(Mono<String> lastName);

    Mono<Person> findByFirstNameAndLastName(Mono<String> firstName, String lastName);

    @Tailable
    Flux<Person> findWithTailableCursorBy();
}

