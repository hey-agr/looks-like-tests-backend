package ru.agr.backend.looksliketests.controller.resources;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Getter
public class TestsResource {
    private final List<TestResource> tests;
}
