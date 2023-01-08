package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.*;
import ru.agr.backend.looksliketests.db.repository.TestResultRepository;
import ru.agr.backend.looksliketests.service.RightAnswerCalculateService;
import ru.agr.backend.looksliketests.service.TestAnswerService;
import ru.agr.backend.looksliketests.service.TestResultService;

import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {
    private final TestResultRepository testResultRepository;
    private final TestAnswerService testAnswerService;
    private final RightAnswerCalculateService rightAnswerCalculateService;

    @Override
    public TestResult save(@NonNull TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    @Override
    public TestResult calculateTestResult(@NonNull TestProgress testProgress) {
        final var test = testProgress.getTest();

        final var resultQuestionAnswersMap = testAnswerService.findByTestProgressId(testProgress.getId()).stream()
                .collect(Collectors.groupingBy(TestAnswer::getQuestion));

        final var testDateStarted = testProgress.getDateStarted();
        final var testDateFinished = testProgress.getDateFinished();

        final var testDeadline = test.getDuration() != 0
                ? testDateStarted.plusSeconds(test.getDuration().longValue())
                : testDateFinished;

        final var expired = testDateFinished.isAfter(testDeadline);
        final var rightAnswersCount = resultQuestionAnswersMap.entrySet().stream()
                .filter(entry -> rightAnswerCalculateService.isRightAnswer(entry.getKey(), entry.getValue()))
                .count();

        return TestResult.builder()
                .testProgressId(testProgress.getId())
                .questionCount((long) test.getQuestions().size())
                .rightAnswersCount(rightAnswersCount)
                .expired(expired)
                .testResultStatus(processTestResultStatus(test, expired, rightAnswersCount))
                .build();
    }

    private TestResultStatus processTestResultStatus(Test test, boolean expired, long rightAnswersCount) {
        if (Boolean.TRUE.equals(test.getNeedVerification())) {
            return TestResultStatus.PENDING;
        } else if (expired) {
            return TestResultStatus.FAILED;
        } else {
            return rightAnswersCount >= test.getMinCorrectAnswers()
                    ? TestResultStatus.PASSED
                    : TestResultStatus.FAILED;
        }
    }
}
