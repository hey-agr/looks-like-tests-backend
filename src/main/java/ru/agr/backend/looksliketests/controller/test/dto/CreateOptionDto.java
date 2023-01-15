package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
@Builder
public class CreateOptionDto {
    @NotEmpty String name;
    Boolean rightAnswer;
}
