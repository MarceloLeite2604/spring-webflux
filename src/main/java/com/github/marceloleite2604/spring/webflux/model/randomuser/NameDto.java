package com.github.marceloleite2604.spring.webflux.model.randomuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class NameDto {

    private final String title;

    private final String first;

    private final String last;
}
