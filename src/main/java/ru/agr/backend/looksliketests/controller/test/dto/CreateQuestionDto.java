package ru.agr.backend.looksliketests.controller.test.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
@Builder
public class CreateQuestionDto {
    @NotEmpty String name;
    @NotNull QuestionType type;
    @Valid List<CreateQuestionImageDto> images;
    @Valid List<CreateOptionDto> answers;
}
