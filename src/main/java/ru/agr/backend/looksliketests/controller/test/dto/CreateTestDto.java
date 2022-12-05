package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class CreateTestDto {
    @NotEmpty String name;
    String description;
    String duration;
    Integer minRightAnswers;
    @Min(1) Integer attempts;
    @NotNull Boolean isNeedVerify;
    @Valid List<CreateQuestionDto> questions;
}
