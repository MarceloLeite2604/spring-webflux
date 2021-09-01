package com.github.marceloleite2604.spring.webflux.service;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.model.mapper.UserDtoToPersonMapper;
import com.github.marceloleite2604.spring.webflux.model.randomuser.ResultsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomUserService {

    private static final String BASE_URL = "https://randomuser.me/api/";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.builder("myConnectionProvider")
                    .maxConnections(3)
                    .pendingAcquireMaxCount(Integer.MAX_VALUE)
                    .build())))
            .build();

    private final UserDtoToPersonMapper userDtoToPersonMapper;

    public Flux<Person> retrieve(final long amount) {
        log.info("Requesting {} users.", amount);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("results", amount)
                        .build())
                .retrieve()
                .bodyToMono(ResultsDto.class)
                .doOnError(WebClientResponseException.class, exception -> log.error("Exception while fetching " + amount + " users: " + exception.getResponseBodyAsString(), exception))
                .flatMapIterable(resultsDto -> userDtoToPersonMapper.mapAllTo(resultsDto.getResults()));
    }
}
