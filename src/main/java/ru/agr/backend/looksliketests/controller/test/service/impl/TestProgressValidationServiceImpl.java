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

import static java.util.Objects.isNull;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class TestProgressValidationServiceImpl implements TestProgressValidationService {
    private static final String TEST_HAS_BEEN_FINISHED_ERROR_MESSAGE = "Test progress with id=%s has been already finished!";
    private static final String WRONG_ANSWERS_SIZE_ERROR_MESSAGE = "Test with id=%s has %s questions, but %s answers have been provided!";
    private static final String QUESTION_NOT_FOUND_IN_TEST_ERROR_MESSAGE = "Question with id=%s is not found in the test with id=%s";
    private static final String WRITING_QUESTION_HAS_OPTION_ANSWERS_ERROR_MESSAGE = "Question with id=%s and type=WRITING shouldn't have any options answers";
    private static final String TEXT_ANSWER_IS_EMPTY_ERROR_MESSAGE = "Text answer is empty for the question with id=%s";
    private static final String NOT_FOUND_OPTIONS_IN_THE_QUESTION_ERROR_MESSAGE = "Not found option ids=%s in the question with id=%s";
    private static final String OPTIONS_HAVEN_NOT_BEEN_PROVIDED_ERROR_MESSAGE = "Options haven't been provided in the answer to the question with id=%s";


    private final TestProgressService testProgressService;

    @Override
    public void validate(@NonNull TestProgress testProgress, @NonNull CreateTestAnswersDto createTestAnswersDto) throws TestProgressValidationException {
        if (testProgressService.isFinished(testProgress)) {
            throw new TestProgressValidationException(String.format(TEST_HAS_BEEN_FINISHED_ERROR_MESSAGE, testProgress.getId()));
        }
        final var test = testProgress.getTest();
        if (test.getQuestions().size() != createTestAnswersDto.getAnswers().size()) {
            throw new TestProgressValidationException(
                    String.format(WRONG_ANSWERS_SIZE_ERROR_MESSAGE,
                            test.getId(), test.getQuestions().size(), createTestAnswersDto.getAnswers().size())
            );
        }
        for (var answer : createTestAnswersDto.getAnswers()) {
            final var question = test.getQuestions().stream()
                    .filter(currentQuestion -> currentQuestion.getId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new TestProgressValidationException(
                            String.format(QUESTION_NOT_FOUND_IN_TEST_ERROR_MESSAGE, answer.getQuestionId(), test.getId())
                    ));
            if (question.getType().isOption()) {
                validateOptionQuestion(answer, question);
            } else if (question.getType() == QuestionType.WRITING) {
                validateWritingQuestion(answer, question);
            }
        }
    }

    private void validateWritingQuestion(CreateTestAnswerDto answer, Question question) throws TestProgressValidationException {
        if (Objects.nonNull(answer.getOptionIds()) && !answer.getOptionIds().isEmpty()) {
            throw new TestProgressValidationException(
                    String.format(WRITING_QUESTION_HAS_OPTION_ANSWERS_ERROR_MESSAGE, question.getId())
            );
        }
        if (isNull(answer.getTextAnswer()) || answer.getTextAnswer().isBlank()) {
            throw new TestProgressValidationException(
                    String.format(TEXT_ANSWER_IS_EMPTY_ERROR_MESSAGE, question.getId())
            );
        }
    }

    private void validateOptionQuestion(CreateTestAnswerDto answer, Question question) throws TestProgressValidationException {
        if (!question.getOptions().isEmpty()
                && (isNull(answer.getOptionIds()) || answer.getOptionIds().isEmpty())) {
            throw new TestProgressValidationException(
                    String.format(OPTIONS_HAVEN_NOT_BEEN_PROVIDED_ERROR_MESSAGE, question.getId())
            );
        }
        final var options = question.getOptions().stream()
                .filter(currentOption -> answer.getOptionIds().contains(currentOption.getId()))
                .collect(Collectors.toSet());
        if (!answer.getOptionIds().isEmpty() && answer.getOptionIds().size() != options.size()) {
            throw new TestProgressValidationException(
                    String.format(NOT_FOUND_OPTIONS_IN_THE_QUESTION_ERROR_MESSAGE, answer.getOptionIds(), question.getId())
            );
        }
    }
}
