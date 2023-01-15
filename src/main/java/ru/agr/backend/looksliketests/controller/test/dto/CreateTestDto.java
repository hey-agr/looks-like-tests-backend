package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @Min(0) Long duration; // sec
    Long minRightAnswers;
    @Min(1) Long attempts;
    @Valid List<CreateQuestionDto> questions;
}
