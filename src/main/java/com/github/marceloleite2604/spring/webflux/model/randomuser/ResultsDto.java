package com.github.marceloleite2604.spring.webflux.model.randomuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class ResultsDto {

    private final Set<UserDto> results;
}
