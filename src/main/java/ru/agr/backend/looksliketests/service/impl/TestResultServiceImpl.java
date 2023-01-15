package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.entity.main.TestResult;
import ru.agr.backend.looksliketests.db.entity.main.TestResultStatus;
import ru.agr.backend.looksliketests.db.repository.TestResultRepository;
import ru.agr.backend.looksliketests.service.RightAnswerCalculateService;
import ru.agr.backend.looksliketests.service.TestAnswerService;
import ru.agr.backend.looksliketests.service.TestResultService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
    @Transactional
    public TestResult save(@NonNull TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    @Override
    public TestResult calculateTestResult(@NonNull TestProgress testProgress) {
        return nonNull(testProgress.getDateFinished())
                ? calculateFinishedTestResult(testProgress)
                : calculateProgressTestResult(testProgress);
    }

    private TestResult calculateFinishedTestResult(@NonNull TestProgress testProgress) {
        final var test = testProgress.getTest();
        final var testDateStarted = testProgress.getDateStarted();
        final var testDateFinished = testProgress.getDateFinished();

        final var testDeadline = test.getDuration() != 0
                ? testDateStarted.plusSeconds(test.getDuration())
                : testDateFinished;

        final var expired = testDateFinished.isAfter(testDeadline);

        final var resultQuestionAnswersMap = testAnswerService.findByTestProgressId(testProgress.getId()).stream()
                .collect(Collectors.groupingBy(TestAnswer::getQuestion));

        final var questionCount = Long.valueOf(test.getQuestions().size());
        final var rightAnswersCount = resultQuestionAnswersMap.entrySet().stream()
                .filter(entry -> rightAnswerCalculateService.isRightAnswer(entry.getKey(), entry.getValue()))
                .count();
        final var wrongAnswersCount = resultQuestionAnswersMap.entrySet().stream()
                .filter(entry -> !entry.getKey().getType().isCheckRequired() && !rightAnswerCalculateService.isRightAnswer(entry.getKey(), entry.getValue()))
                .count();
        final var pendingAnswersCount = resultQuestionAnswersMap.keySet().stream()
                .filter(question -> question.getType().isCheckRequired())
                .count();

        return TestResult.builder()
                .testProgressId(testProgress.getId())
                .questionCount(questionCount)
                .rightAnswersCount(rightAnswersCount)
                .wrongAnswersCount(wrongAnswersCount)
                .pendingAnswersCount(pendingAnswersCount)
                .expired(expired)
                .testResultStatus(processTestResultStatus(test, expired, rightAnswersCount))
                .build();
    }

    private TestResult calculateProgressTestResult(@NonNull TestProgress testProgress) {
        final var test = testProgress.getTest();
        final var questionCount = Long.valueOf(test.getQuestions().size());
        return TestResult.builder()
                .testProgressId(testProgress.getId())
                .questionCount(questionCount)
                .rightAnswersCount(0L)
                .wrongAnswersCount(0L)
                .pendingAnswersCount(0L)
                .expired(false)
                .testResultStatus(TestResultStatus.IN_PROGRESS)
                .build();
    }

    @Override
    public List<TestResult> findByTestProgressIds(@NonNull Long... testProgressIds) {
        return testResultRepository.findAllByTestProgressIdIn(testProgressIds);
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
