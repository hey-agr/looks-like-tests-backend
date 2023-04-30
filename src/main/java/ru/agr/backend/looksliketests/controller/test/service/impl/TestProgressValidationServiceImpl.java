package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswersDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestProgressValidationException;
import ru.agr.backend.looksliketests.controller.test.service.TestProgressValidationService;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.service.TestProgressService;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class TestProgressValidationServiceImpl implements TestProgressValidationService {
    private final TestProgressService testProgressService;

    @Override
    public void validate(@NonNull TestProgress testProgress, @NonNull CreateTestAnswersDto createTestAnswersDto) throws TestProgressValidationException {
        if (testProgressService.isFinished(testProgress)) {
            throw new TestProgressValidationException("Test progress with id="+testProgress.getId()+" has been already finished!");
        }

        final var test = testProgress.getTest();
        for (var answer : createTestAnswersDto.getAnswers()) {
            final var question = test.getQuestions().stream()
                    .filter(currentQuestion -> currentQuestion.getId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new TestProgressValidationException("Question with id =" +answer.getQuestionId()+" is not found in the test with id ="+test.getId()));
            if (question.getType().isOption()) {
                validateOptionQuestion(answer, question);
            } else if (question.getType() == QuestionType.WRITING) {
                validateWritingQuestion(answer, question);
            }
        }
    }

    private void validateWritingQuestion(CreateTestAnswerDto answer, Question question) throws TestProgressValidationException {
        if (Objects.nonNull(answer.getOptionIds()) && !answer.getOptionIds().isEmpty()) {
            throw new TestProgressValidationException("Question with with id="+ question.getId()+" and type WRITING shouldn't have options");
        }
        if (Objects.isNull(answer.getTextAnswer()) || answer.getTextAnswer().isBlank()) {
            throw new TestProgressValidationException("Text answer is empty for the question with id="+ question.getId());
        }
    }

    private void validateOptionQuestion(CreateTestAnswerDto answer, Question question) throws TestProgressValidationException {
        final var options = question.getOptions().stream()
                .filter(currentOption -> answer.getOptionIds().contains(currentOption.getId()))
                .collect(Collectors.toSet());
        if (!answer.getOptionIds().isEmpty() && answer.getOptionIds().size() != options.size()) {
            throw new TestProgressValidationException("Not found option ids: " + answer.getOptionIds() + " in the question with id: " + question.getId());
        }
    }
}
