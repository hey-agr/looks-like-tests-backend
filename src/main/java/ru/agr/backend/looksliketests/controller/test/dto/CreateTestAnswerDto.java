package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
@Builder
public class CreateTestAnswerDto {
    @NotNull Long questionId;
    List<Long> optionIds;
    String textAnswer;
}
