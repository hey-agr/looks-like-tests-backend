package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.*;
import ru.agr.backend.looksliketests.db.repository.TestResultRepository;
import ru.agr.backend.looksliketests.service.impl.TestResultServiceImpl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestResultServiceTest {
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "JD@email.com";
    private static final Long USER_ID = 2L;
    private static final Long TEST_PROGRESS_ID = 234L;
    private static final Long TEST_ID = 9583L;
    private static final Long QUESTION1_ID = 8483L;
    private static final Long QUESTION2_ID = 3748L;
    private static final Long TEST_ANSWER_ID = 847L;
    private static final Long TEST_ANSWER2_ID = 848L;

    private User givenUser;
    private TestEntity givenTest;
    private Question givenFirstQuestion;
    private Question givenSecondQuestion;
    private Option givenFirstQuestionFirstOption;
    private Option givenFirstQuestionSecondOption;

    @Mock
    private TestResultRepository testResultRepository;
    @Mock
    private TestAnswerService testAnswerService;
    @Mock
    private RightAnswerCalculateService rightAnswerCalculateService;

    @InjectMocks
    private TestResultServiceImpl service;

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
                .minCorrectAnswers(1L)
                .needVerification(true)
                .build();

        givenFirstQuestion = Question.builder()
                .type(QuestionType.OPTIONS)
                .test(givenTest)
                .name("How are you doing?")
                .build();

        givenFirstQuestionFirstOption = Option.builder()
                .rightAnswer(true)
                .question(givenFirstQuestion)
                .name("I'm fine")
                .build();

        givenFirstQuestionSecondOption = Option.builder()
                .rightAnswer(false)
                .question(givenFirstQuestion)
                .name("Not really good")
                .build();

        givenFirstQuestion.setOptions(Set.of(
                givenFirstQuestionFirstOption,
                givenFirstQuestionSecondOption
        ));

        givenSecondQuestion = Question.builder()
                .type(QuestionType.WRITING)
                .id(QUESTION2_ID)
                .test(givenTest)
                .name("Where are you from?")
                .build();

        givenTest.setQuestions(Set.of(givenFirstQuestion, givenSecondQuestion));
    }

    @Test
    void save() {
        var givenTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.IN_PROGRESS)
                .expired(false)
                .pendingAnswersCount(1L)
                .wrongAnswersCount(1L)
                .rightAnswersCount(1L)
                .questionCount(3L)
                .build();

        service.save(givenTestResult);

        verify(testResultRepository, times(1))
                .save(givenTestResult);
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class,
                () -> service.save(null));
    }

    @Test
    void calculateTestResultInProgressTest() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(ZonedDateTime.now())
                .build();

        var expectedTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.IN_PROGRESS)
                .expired(false)
                .pendingAnswersCount(0L)
                .wrongAnswersCount(0L)
                .rightAnswersCount(0L)
                .questionCount((long) givenTest.getQuestions().size())
                .build();

        assertEquals(expectedTestResult,
                service.calculateTestResult(givenTestProgress));
    }

    @Test
    void calculateTestResultFinishedTestPassed() {
        givenTest.setNeedVerification(false);

        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(ZonedDateTime.now())
                .dateFinished(ZonedDateTime.now().plusMinutes(10))
                .build();

        var givenTestAnswer = TestAnswer.builder()
                .id(TEST_ANSWER_ID)
                .testProgress(givenTestProgress)
                .option(givenFirstQuestionFirstOption)
                .question(givenFirstQuestion)
                .build();

        when(testAnswerService.findByTestProgressId(TEST_PROGRESS_ID))
                .thenReturn(List.of(givenTestAnswer));
        when(rightAnswerCalculateService.isRightAnswer(givenFirstQuestion, List.of(givenTestAnswer)))
                .thenReturn(true);

        var expectedTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.PASSED)
                .expired(false)
                .pendingAnswersCount(0L)
                .wrongAnswersCount(0L)
                .rightAnswersCount(1L)
                .questionCount((long) givenTest.getQuestions().size())
                .build();

        assertEquals(expectedTestResult,
                service.calculateTestResult(givenTestProgress));
    }

    @Test
    void calculateTestResultFinishedTestFailed() {
        givenTest.setNeedVerification(false);

        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(ZonedDateTime.now())
                .dateFinished(ZonedDateTime.now().plusMinutes(10))
                .build();

        var givenTestAnswer = TestAnswer.builder()
                .id(TEST_ANSWER_ID)
                .testProgress(givenTestProgress)
                .option(givenFirstQuestionSecondOption)
                .question(givenFirstQuestion)
                .build();

        when(testAnswerService.findByTestProgressId(TEST_PROGRESS_ID))
                .thenReturn(List.of(givenTestAnswer));
        when(rightAnswerCalculateService.isRightAnswer(givenFirstQuestion, List.of(givenTestAnswer)))
                .thenReturn(false);

        var expectedTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.FAILED)
                .expired(false)
                .pendingAnswersCount(0L)
                .wrongAnswersCount(1L)
                .rightAnswersCount(0L)
                .questionCount((long) givenTest.getQuestions().size())
                .build();

        assertEquals(expectedTestResult,
                service.calculateTestResult(givenTestProgress));
    }

    @Test
    void calculateTestResultFinishedTestExpired() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(ZonedDateTime.now())
                .dateFinished(ZonedDateTime.now().plusDays(10))
                .build();

        var givenTestAnswer = TestAnswer.builder()
                .id(TEST_ANSWER_ID)
                .testProgress(givenTestProgress)
                .option(givenFirstQuestionSecondOption)
                .question(givenFirstQuestion)
                .build();

        var givenSecondTestAnswer = TestAnswer.builder()
                .id(TEST_ANSWER2_ID)
                .testProgress(givenTestProgress)
                .question(givenSecondQuestion)
                .textAnswer("I am from Santander")
                .build();

        when(testAnswerService.findByTestProgressId(TEST_PROGRESS_ID))
                .thenReturn(List.of(givenTestAnswer, givenSecondTestAnswer));
        when(rightAnswerCalculateService.isRightAnswer(givenFirstQuestion, List.of(givenTestAnswer)))
                .thenReturn(false);
        when(rightAnswerCalculateService.isRightAnswer(givenSecondQuestion, List.of(givenSecondTestAnswer)))
                .thenReturn(false);

        var expectedTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.PENDING)
                .expired(true)
                .pendingAnswersCount(1L)
                .wrongAnswersCount(1L)
                .rightAnswersCount(0L)
                .questionCount((long) givenTest.getQuestions().size())
                .build();

        assertEquals(expectedTestResult,
                service.calculateTestResult(givenTestProgress));
    }

    @Test
    void calculateTestResultNPE() {
        assertThrows(NullPointerException.class,
                () -> service.calculateTestResult(null));
    }

    @Test
    void findByTestProgressIds() {
        var expectedTestResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultStatus.FAILED)
                .expired(true)
                .pendingAnswersCount(0L)
                .wrongAnswersCount(1L)
                .rightAnswersCount(0L)
                .questionCount((long) givenTest.getQuestions().size())
                .build();

        when(testResultRepository.findAllByTestProgressIdIn(new Long[]{TEST_PROGRESS_ID}))
                .thenReturn(List.of(expectedTestResult));

        assertEquals(List.of(expectedTestResult),
                service.findByTestProgressIds(TEST_PROGRESS_ID));
    }
}