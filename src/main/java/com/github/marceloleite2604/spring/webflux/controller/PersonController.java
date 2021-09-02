package com.github.marceloleite2604.spring.webflux.controller;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping(PersonController.BASE_PATH)
public class PersonController {

    static final String BASE_PATH = "/person";

    private final PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    private Flux<Person> get() {
        return personService.retrieveAll();
    }
}