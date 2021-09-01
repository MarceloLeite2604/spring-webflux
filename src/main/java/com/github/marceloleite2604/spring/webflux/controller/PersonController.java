package com.github.marceloleite2604.spring.webflux.controller;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.repository.ReactivePersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController("/person")
public class PersonController {

    private final ReactivePersonRepository reactivePersonRepository;

    @GetMapping("/{id}")
    private Mono<Person> getPersonById(@PathVariable UUID id) {
        return reactivePersonRepository.findById(id);
    }

    @GetMapping
    private Flux<Person> getAllPersons() {
        return reactivePersonRepository.findAll();
    }
}