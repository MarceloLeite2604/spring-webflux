package com.github.marceloleite2604.spring.webflux.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.spring.webflux.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonToJsonStringMapper extends AbstractObjectToJsonStringMapper<Person> {

    public PersonToJsonStringMapper(ObjectMapper objectMapper) {
        super(objectMapper, Person.class);
    }
}
