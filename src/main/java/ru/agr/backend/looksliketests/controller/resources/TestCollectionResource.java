package ru.agr.backend.looksliketests.controller.resources;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.controller.resources.base.PageableResource;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
public class TestCollectionResource extends PageableResource {
    private final List<TestResource> tests;
}
