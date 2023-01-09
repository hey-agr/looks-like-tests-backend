package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.test.dto.CreateOptionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestValidationException;
import ru.agr.backend.looksliketests.controller.test.service.TestValidationService;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Service
public class TestValidationServiceImpl implements TestValidationService {
    @Override
    public void validate(@NonNull CreateTestDto createTestDto) throws TestValidationException {
        validateQuestions(createTestDto);
    }

    private void validateQuestions(@NonNull CreateTestDto createTestDto) throws TestValidationException {
        if (createTestDto.getMinRightAnswers() > createTestDto.getQuestions().size()) {
            throw new TestValidationException("Minimum right answers cannot be greater then questions size!");
        }
        for (var question : createTestDto.getQuestions()) {
            validateQuestion(question);
        }
    }

    private void validateQuestion(@NonNull CreateQuestionDto createQuestionDto) throws TestValidationException {
        if (Objects.requireNonNull(createQuestionDto.getType()) == QuestionType.OPTIONS) {
            validateQuestionOptionType(createQuestionDto.getAnswers());
        } else if (createQuestionDto.getType() == QuestionType.OPTIONS_MULTIPLY) {
            validateQuestionOptionMultiplyType(createQuestionDto.getAnswers());
        } else if (createQuestionDto.getType() == QuestionType.WRITING) {
            validateQuestionWriting(createQuestionDto);
        }
    }

    private void validateQuestionOptionType(@NonNull List<CreateOptionDto> answers) throws TestValidationException {
        if (answers.stream().filter(answer-> nonNull(answer.getRightAnswer())).filter(CreateOptionDto::getRightAnswer).count() != 1) {
            throw new TestValidationException("Question with type OPTIONS must have one right answer");
        }
    }

    private void validateQuestionOptionMultiplyType(@NonNull List<CreateOptionDto> answers) throws TestValidationException {
        if (answers.stream().filter(answer-> nonNull(answer.getRightAnswer())).filter(CreateOptionDto::getRightAnswer).count() <= 1) {
            throw new TestValidationException("Question with type OPTIONS_MULTIPLY must have at least two right answers");
        }
    }

    private void validateQuestionWriting(@NonNull CreateQuestionDto createQuestionDto) throws TestValidationException {
        if (nonNull(createQuestionDto.getAnswers()) && !createQuestionDto.getAnswers().isEmpty()) {
            throw new TestValidationException("Question with type WRITING shouldn't have answers");
        }
    }
}
