package ru.agr.backend.looksliketests.controller.test.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class CreateQuestionImageDto {
    @NotEmpty String url;
}
