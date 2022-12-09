package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.test.dto.CreateOptionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestValidationException;
import ru.agr.backend.looksliketests.controller.test.service.TestValidationService;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Service
public class TestValidationServiceImpl implements TestValidationService {
    @Override
    public void validate(@NonNull CreateTestDto createTestDto) throws TestValidationException {
        for (var question : createTestDto.getQuestions()) {
            validateQuestion(question);
        }
    }

    private void validateQuestion(@NonNull CreateQuestionDto createQuestionDto) throws TestValidationException {
        switch (createQuestionDto.getType()) {
            case OPTIONS -> validateQuestionOptionType(createQuestionDto.getAnswers());
            case OPTIONS_MULTIPLY -> validateQuestionOptionMultiplyType(createQuestionDto.getAnswers());
        }
    }

    private void validateQuestionOptionType(@NonNull List<CreateOptionDto> answers) throws TestValidationException {
        if (answers.stream().filter(CreateOptionDto::isRightAnswer).count() != 1) {
            throw new TestValidationException("Question with type OPTIONS must have one right answer");
        }
    }

    private void validateQuestionOptionMultiplyType(@NonNull List<CreateOptionDto> answers) throws TestValidationException {
        if (answers.stream().filter(CreateOptionDto::isRightAnswer).count() <= 1) {
            throw new TestValidationException("Question with type OPTIONS_MULTIPLY must have at least two right answers");
        }
    }
}
