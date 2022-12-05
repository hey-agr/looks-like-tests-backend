package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Valid List<CreateOptionDto> answers;
}
