package com.github.marceloleite2604.spring.webflux.service;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.repository.ReactivePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final ReactivePersonRepository reactivePersonRepository;

    public void save(Person person) {
        log.info("Saving on database: {}", person);
        reactivePersonRepository.insert(person);
    }

    public Mono<Long> count() {
        return reactivePersonRepository.count();
    }
}
