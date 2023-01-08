package ru.agr.backend.looksliketests.controller.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestProgressResource {
    Long id;
    UserResource user;
    TestResource test;
    ZonedDateTime dateStarted;
    ZonedDateTime dateFinished;
}
