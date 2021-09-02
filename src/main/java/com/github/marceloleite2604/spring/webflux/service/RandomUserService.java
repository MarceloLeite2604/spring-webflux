package com.github.marceloleite2604.spring.webflux.service;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.model.mapper.UserDtoToPersonMapper;
import com.github.marceloleite2604.spring.webflux.model.randomuser.ResultsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.time.Duration;

@Service
@Slf4j
public class RandomUserService {

    private static final String BASE_URL = "https://randomuser.me/api/";

    private final WebClient webClient;

    private final UserDtoToPersonMapper userDtoToPersonMapper;

    public RandomUserService(UserDtoToPersonMapper userDtoToPersonMapper) {
        this.webClient = createWebClient();
        this.userDtoToPersonMapper = userDtoToPersonMapper;
    }

    private WebClient createWebClient() {
        final var connectionProvider = ConnectionProvider.builder("randomUserConnectionProvider")
                .maxConnections(5)
                .pendingAcquireMaxCount(Integer.MAX_VALUE)
                .build();

        final var httpClient = HttpClient.create(connectionProvider);

        final var reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(reactorClientHttpConnector)
                .build();
    }

    public Flux<Person> retrieve(final long amount) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("results", amount)
                        .build())
                .retrieve()
                .bodyToMono(ResultsDto.class)
                .retryWhen(Retry.backoff(6, Duration.ofMillis(200))
                        .doBeforeRetry(retrySignal -> log.warn("User retrieval failed on attempt {}.", retrySignal.totalRetries() + 1))
                        .onRetryExhaustedThrow(
                                (retryBackoffSpec, retrySignal) ->
                                        new ConnectException(
                                                String.format("Could not retrieve %d user(s) after %d attempt(s).", amount, retryBackoffSpec.maxAttempts))
                        ))
                .flatMapIterable(resultsDto -> userDtoToPersonMapper.mapAllTo(resultsDto.getResults()));
    }
}
