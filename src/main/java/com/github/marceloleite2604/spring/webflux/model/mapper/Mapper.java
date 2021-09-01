package com.github.marceloleite2604.spring.webflux.model.mapper;

public interface Mapper <I,O> {

    default O mapTo(I object) {
        throw new UnsupportedOperationException();
    }

    default I mapFrom(O object) {
        throw new UnsupportedOperationException();
    }
}
