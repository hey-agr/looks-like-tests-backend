package ru.agr.backend.looksliketests.controller.resources;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@SuperBuilder
@Getter
public class TestsResource extends PageableResource {
    private final List<TestResource> tests;
}
