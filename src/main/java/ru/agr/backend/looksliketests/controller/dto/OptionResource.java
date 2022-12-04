package ru.agr.backend.looksliketests.controller.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Value
@Builder
public class OptionResource {
    Long id;
    String name;
}
