package com.github.marceloleite2604.spring.webflux.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Person {

    @Id
    @EqualsAndHashCode.Include
    private final UUID id;

    private final String firstName;

    private final String lastName;
}
