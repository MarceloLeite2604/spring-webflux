package com.github.marceloleite2604.spring.webflux.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<I, O> implements Mapper<I, O> {

    public List<O> mapAllTo(Collection<I> objects) {
        return objects.stream()
                .map(this::mapTo)
                .collect(Collectors.toList());
    }

    public List<I> mapAllFrom(Collection<O> objects) {
        return objects.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }
}
