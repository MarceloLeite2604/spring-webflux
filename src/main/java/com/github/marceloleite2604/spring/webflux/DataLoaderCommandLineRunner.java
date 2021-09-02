package com.github.marceloleite2604.spring.webflux;

import com.github.marceloleite2604.spring.webflux.service.PersonService;
import com.github.marceloleite2604.spring.webflux.service.RandomUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoaderCommandLineRunner implements CommandLineRunner {

    private static final long REQUIRED_PERSONS_ON_DATABASE = 20_000L;

    private static final long MAX_PERSONS_PER_REQUEST = 32L;

    private final PersonService personService;

    private final RandomUserService randomUserService;

    @Override
    public void run(String... args) {
        log.info("Checking how many persons do we have on database.");
        personService.count()
                .subscribe(this::checkTotalPersons);
    }

    private void checkTotalPersons(final long totalPersons) {
        if (totalPersons >= REQUIRED_PERSONS_ON_DATABASE) {
            log.info("We found {} persons on database, so we will not load any more persons.", totalPersons);
            return;
        }

        final var remainingPersons = REQUIRED_PERSONS_ON_DATABASE - totalPersons;
        log.info("We still need {} persons on database. Adding them now.", remainingPersons);

        addPersonsOnDatabase(remainingPersons);
    }

    private void addPersonsOnDatabase(final long amount) {
        final List<Long> amountPerRequest = createAmountPerRequests(amount);

        Flux.fromIterable(amountPerRequest)
                .flatMap(randomUserService::retrieve)
                .flatMap(personService::save)
                .doOnComplete(() ->
                        personService.count()
                                .subscribe(totalPersons ->
                                        log.info("{} persons added. We have a total of {} persons on database.", amount, totalPersons)))
                .doOnError(throwable -> log.error("Error while fetching users", throwable))
                .subscribe();
    }

    private List<Long> createAmountPerRequests(final long amount) {
        var remaining = amount;
        List<Long> amountPerRequest = new LinkedList<>();

        while (remaining > 0) {
            final var amountRequested = Math.min(remaining, MAX_PERSONS_PER_REQUEST);
            amountPerRequest.add(amountRequested);
            remaining -= amountRequested;
        }
        return amountPerRequest;
    }
}
