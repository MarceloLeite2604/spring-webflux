package com.github.marceloleite2604.spring.webflux.model.mapper;

import com.github.marceloleite2604.spring.webflux.model.Person;
import com.github.marceloleite2604.spring.webflux.model.randomuser.UserDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDtoToPersonMapper extends AbstractMapper<UserDto, Person> {

    @Override
    public Person mapTo(UserDto userDto) {
        final var name = userDto.getName();
        return Person.builder()
                .id(UUID.randomUUID())
                .firstName(name.getFirst())
                .lastName(name.getLast())
                .build();
    }
}
