package com.github.marceloleite2604.spring.webflux.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractObjectToJsonStringMapper<I> extends AbstractMapper<I, String> {

    private final ObjectMapper objectMapper;

    private final Class<I> clazz;

    public I mapFrom(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException exception) {
            final var message = String.format("Exception thrown while converting JSON string into and instance of %s class.", clazz.getSimpleName());
            throw new IllegalArgumentException(message, exception);
        }
    }

    public String mapTo(I object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            final var message = String.format("Exception thrown while writing instance of %s class.", clazz.getSimpleName());
            throw new IllegalArgumentException(message, exception);
        }
    }
}
