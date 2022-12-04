package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
public class TestsResource {
    private final List<TestResource> tests;
}
