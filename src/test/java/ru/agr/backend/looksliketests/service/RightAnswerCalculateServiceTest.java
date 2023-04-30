package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.service.impl.RightAnswerCalculateServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arslan Rabadanov
 */
class RightAnswerCalculateServiceTest {
    private static final Long OPTION_ID1 = 1L;
    private static final Long OPTION_ID2 = 2L;
    private static final Long OPTION_ID3 = 3L;

    private final RightAnswerCalculateService service = new RightAnswerCalculateServiceImpl();

    @Test
    void isRightAnswerEmptyAnswersFalse() {
        var givenQuestion = Question.builder().build();
        assertFalse(service.isRightAnswer(givenQuestion, Collections.emptyList()));
    }

    @Test
    void isRightAnswerNPE() {
        assertThrows(NullPointerException.class,
                () -> service.isRightAnswer(null, null));
        var givenQuestion = Question.builder().build();
        assertThrows(NullPointerException.class,
                () -> service.isRightAnswer(givenQuestion, null));
    }

    @Test
    void isRightAnswerOptionTrue() {
        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenWrongOption = Option.builder()
                .id(OPTION_ID2)
                .rightAnswer(Boolean.FALSE)
                .build();

        var givenQuestion = Question.builder()
                .type(QuestionType.OPTIONS)
                .options(List.of(givenRightOption, givenWrongOption))
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenRightOption)
                        .build()
        );

        assertTrue(service.isRightAnswer(givenQuestion, givenAnswers));
    }

    @Test
    void isRightAnswerOptionFalse() {
        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenWrongOption = Option.builder()
                .id(OPTION_ID2)
                .rightAnswer(Boolean.FALSE)
                .build();

        var givenQuestion = Question.builder()
                .type(QuestionType.OPTIONS)
                .options(List.of(givenRightOption, givenWrongOption))
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenWrongOption)
                        .build()
        );

        assertFalse(service.isRightAnswer(givenQuestion, givenAnswers));
    }

    @Test
    void isRightAnswerOptionMultiplyTrue() {
        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenSecondRightOption = Option.builder()
                .id(OPTION_ID2)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenWrongOption = Option.builder()
                .id(OPTION_ID3)
                .rightAnswer(Boolean.FALSE)
                .build();

        var givenQuestion = Question.builder()
                .type(QuestionType.OPTIONS_MULTIPLY)
                .options(List.of(givenRightOption, givenSecondRightOption, givenWrongOption))
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenRightOption)
                        .build(),
                TestAnswer.builder()
                        .option(givenSecondRightOption)
                        .build()
        );

        assertTrue(service.isRightAnswer(givenQuestion, givenAnswers));
    }

    @Test
    void isRightAnswerOptionMultiplyFalse() {
        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenSecondRightOption = Option.builder()
                .id(OPTION_ID2)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenWrongOption = Option.builder()
                .id(OPTION_ID3)
                .rightAnswer(Boolean.FALSE)
                .build();

        var givenQuestion = Question.builder()
                .type(QuestionType.OPTIONS_MULTIPLY)
                .options(List.of(givenRightOption, givenSecondRightOption, givenWrongOption))
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenRightOption)
                        .build(),
                TestAnswer.builder()
                        .option(givenWrongOption)
                        .build()
        );

        assertFalse(service.isRightAnswer(givenQuestion, givenAnswers));
    }

    @Test
    void isRightAnswerOptionMultiplyOnlyOneRightFalse() {
        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenSecondRightOption = Option.builder()
                .id(OPTION_ID2)
                .rightAnswer(Boolean.TRUE)
                .build();
        var givenWrongOption = Option.builder()
                .id(OPTION_ID3)
                .rightAnswer(Boolean.FALSE)
                .build();

        var givenQuestion = Question.builder()
                .type(QuestionType.OPTIONS_MULTIPLY)
                .options(List.of(givenRightOption, givenSecondRightOption, givenWrongOption))
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenRightOption)
                        .build()
        );

        assertFalse(service.isRightAnswer(givenQuestion, givenAnswers));
    }

    @Test
    void isRightAnswerWritingFalse() {
        var givenQuestion = Question.builder()
                .type(QuestionType.WRITING)
                .build();

        var givenRightOption = Option.builder()
                .id(OPTION_ID1)
                .rightAnswer(Boolean.TRUE)
                .build();

        var givenAnswers = List.of(
                TestAnswer.builder()
                        .option(givenRightOption)
                        .build()
        );

        assertFalse(service.isRightAnswer(givenQuestion, givenAnswers));
    }
}