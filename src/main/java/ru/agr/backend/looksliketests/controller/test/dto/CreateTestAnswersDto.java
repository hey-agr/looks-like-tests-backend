package ru.agr.backend.looksliketests.controller.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
@Builder
public class CreateTestAnswersDto {
    @NotNull List<CreateTestAnswerDto> answers;
}
