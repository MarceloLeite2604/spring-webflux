package com.github.marceloleite2604.spring.webflux.model.randomuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class UserDto {

    private final NameDto name;
}
