package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswersDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestProgressValidationException;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.*;
import ru.agr.backend.looksliketests.service.TestProgressService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestProgressValidationServiceImplTest {
    private static final Long TEST_ID = 9583L;
    private static final Long TEST_PROGRESS_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "JD@email.com";
    private static final Long USER_ID = 2L;

    private static final Long QUESTION1_ID = 8483L;
    private static final Long OPTION1_ID = 38L;
    private static final Long OPTION2_ID = 39L;
    private static final Long QUESTION2_ID = 3748L;
    private static final Long WRONG_QUESTION_ID = 8372839329774389L;
    private static final Long WRONG_OPTION_ID = 9373288847L;


    @Mock
    private TestProgressService testProgressService;

    @InjectMocks
    private TestProgressValidationServiceImpl service;

    private User givenUser;
    private TestEntity givenTest;
    private Question givenFirstQuestion;
    private Question givenSecondQuestion;
    private Option givenFirstQuestionFirstOption;
    private Option givenFirstQuestionSecondOption;

    @BeforeEach
    void setUp() {
        givenUser = User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .activated(true)
                .build();

        givenTest = TestEntity.builder()
                .id(TEST_ID)
                .attempts(2L)
                .name("Some test")
                .description("Some test description")
                .duration(3600L)
                .minCorrectAnswers(2L)
                .needVerification(false)
                .build();

        givenFirstQuestion = Question.builder()
                .id(QUESTION1_ID)
                .type(QuestionType.OPTIONS)
                .test(givenTest)
                .name("How are you doing?")
                .build();

        givenFirstQuestionFirstOption = Option.builder()
                .id(OPTION1_ID)
                .rightAnswer(true)
                .question(givenFirstQuestion)
                .name("I'm fine")
                .build();

        givenFirstQuestionSecondOption = Option.builder()
                .id(OPTION2_ID)
                .rightAnswer(false)
                .question(givenFirstQuestion)
                .name("Not really good")
                .build();

        givenFirstQuestion.setOptions(Set.of(
                givenFirstQuestionFirstOption,
                givenFirstQuestionSecondOption
        ));

        givenSecondQuestion = Question.builder()
                .id(QUESTION2_ID)
                .type(QuestionType.WRITING)
                .test(givenTest)
                .name("Where are you from?")
                .build();

        givenTest.setQuestions(Set.of(givenFirstQuestion, givenSecondQuestion));
    }

    @Test
    void validateNPE() {
        var givenTestProgress = TestProgress.builder()
                .dateFinished(ZonedDateTime.now().plusDays(1))
                .build();
        assertThrows(NullPointerException.class,
                () -> service.validate(null, null));
        assertThrows(NullPointerException.class,
                () -> service.validate(givenTestProgress, null));
    }

    @Test
    void validateFinished() {
        var givenTestProgress = TestProgress.builder()
                .dateFinished(ZonedDateTime.now().plusDays(1))
                .build();

        var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .build();

        when(testProgressService.isFinished(givenTestProgress))
                .thenReturn(true);

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDto));
    }

    @Test
    void validateWrongAnswersCountProvided() {
        var givenTestProgress = TestProgress.builder()
                .test(givenTest)
                .build();

        var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .answers(List.of())
                .build();

        when(testProgressService.isFinished(givenTestProgress))
                .thenReturn(false);

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDto));
    }

    @Test
    void validateQuestionIsNotFoundInTheTest() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(ZonedDateTime.now())
                .build();

        var givenAnswerOnFirstQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION1_ID)
                .optionIds(List.of(OPTION1_ID))
                .build();

        var givenAnswerOnSecondQuestion = CreateTestAnswerDto.builder()
                .questionId(WRONG_QUESTION_ID)
                .textAnswer("Hey!")
                .build();

        var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .answers(List.of(givenAnswerOnFirstQuestion, givenAnswerOnSecondQuestion))
                .build();

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDto));
    }

    @Test
    void validateOptionsHaveNotBeenProvidedInTheAnswer() {
        final var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(ZonedDateTime.now())
                .build();

        //CASE 1: empty
        var givenAnswerOnFirstQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION1_ID)
                .optionIds(List.of())
                .build();

        var givenAnswerOnSecondQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION2_ID)
                .textAnswer("Hey!")
                .build();

        final var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .answers(List.of(givenAnswerOnFirstQuestion, givenAnswerOnSecondQuestion))
                .build();

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDto));

        //CASE 2: null
        givenAnswerOnFirstQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION1_ID)
                .optionIds(null)
                .build();

        final var givenCreateTestAnswerDtoNullCase = CreateTestAnswersDto.builder()
                .answers(List.of(givenAnswerOnFirstQuestion, givenAnswerOnSecondQuestion))
                .build();

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDtoNullCase));
    }

    @Test
    void validateOptionIsNotFoundInTheQuestion() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(ZonedDateTime.now())
                .build();

        var givenAnswerOnFirstQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION1_ID)
                .optionIds(List.of(WRONG_OPTION_ID))
                .build();

        var givenAnswerOnSecondQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION2_ID)
                .textAnswer("Hey!")
                .build();

        var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .answers(List.of(givenAnswerOnFirstQuestion, givenAnswerOnSecondQuestion))
                .build();

        assertThrows(TestProgressValidationException.class,
                () -> service.validate(givenTestProgress, givenCreateTestAnswerDto));
    }

    @SneakyThrows
    @Test
    void validateOption() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(ZonedDateTime.now())
                .build();

        var givenAnswerOnFirstQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION1_ID)
                .optionIds(List.of(OPTION1_ID))
                .build();

        var givenAnswerOnSecondQuestion = CreateTestAnswerDto.builder()
                .questionId(QUESTION2_ID)
                .textAnswer("Hey!")
                .build();

        var givenCreateTestAnswerDto = CreateTestAnswersDto.builder()
                .answers(List.of(givenAnswerOnFirstQuestion, givenAnswerOnSecondQuestion))
                .build();

        service.validate(givenTestProgress, givenCreateTestAnswerDto);

        verify(testProgressService, times(1))
                .isFinished(givenTestProgress);
    }
}