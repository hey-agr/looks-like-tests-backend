package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationsResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.resources.TestsResource;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestProgressMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestResultMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.entity.main.TestResult;
import ru.agr.backend.looksliketests.service.OptionService;
import ru.agr.backend.looksliketests.service.QuestionService;
import ru.agr.backend.looksliketests.service.TestProgressService;
import ru.agr.backend.looksliketests.service.TestResultService;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class TestResourceServiceImpl implements TestResourceService {
    private final QuestionService questionService;
    private final OptionService optionService;
    private final TestMapper testMapper;
    private final TestProgressService testProgressService;
    private final TestResultService testResultService;
    private final TestResultMapper testResultMapper;
    private final TestProgressMapper testProgressMapper;

    @Override
    public void populateTest(@NonNull Test... tests) {
        final var testIds = Arrays.stream(tests)
                .map(Test::getId)
                .distinct()
                .toArray(Long[]::new);
        final var testQuestions = questionService.findByTestIds(testIds);
        final var testQuestionsMap = testQuestions.stream()
                .collect(Collectors.groupingBy(question -> question.getTest().getId()));
        final var questionIds = testQuestions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
        final var questionOptionsMap = optionService.findByQuestionIds(questionIds).stream()
                .collect(Collectors.groupingBy(option -> option.getQuestion().getId()));
        testQuestions.forEach(testQuestion ->
                testQuestion.setOptions(questionOptionsMap.getOrDefault(testQuestion.getId(), null)));
        Arrays.stream(tests).forEach(test ->
                test.setQuestions(testQuestionsMap.getOrDefault(test.getId(), null)));
    }

    @Override
    public TestsResource prepareTestsResource(@NonNull Page<Test> testsPage, @NonNull Pageable pageable) {
        final var tests = testsPage.getContent();
        populateTest(tests.toArray(new Test[0]));
        return TestsResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(tests.size())
                .totalSize(testsPage.getTotalElements())
                .totalPages(testsPage.getTotalPages())
                .tests(tests.stream().map(testMapper::toResource).toList())
                .build();
    }

    @Override
    public StudentTestAssignationsResource prepareStudentTestAssignationsResource(@NonNull User user,
                                                                                  @NonNull Page<Test> testsPage,
                                                                                  @NonNull Pageable pageable) {
        final var tests = testsPage.getContent();
        populateTest(tests.toArray(new Test[0]));
        final var testIds = tests.stream()
                .map(Test::getId)
                .distinct()
                .toArray(Long[]::new);
        final var testProgressesMap = testProgressService.findAllByUserIdAndTestIds(user.getId(), testIds).stream()
                .collect(Collectors.groupingBy(testProgress -> testProgress.getTest().getId()));
        final var testProgressIds = testProgressesMap.values().stream()
                .flatMap(Collection::stream)
                .map(TestProgress::getId)
                .distinct()
                .toArray(Long[]::new);
        final var testResultsMap = testResultService.findByTestProgressIds(testProgressIds).stream().
                collect(Collectors.groupingBy(TestResult::getTestProgressId));

        final var testResources = tests.stream()
                .map(testMapper::toStudentTestAssignedResource)
                .map(resource -> {
                    final var testProgresses = testProgressesMap.get(resource.getTestId()).stream()
                            .map(testProgressMapper::toResource)
                            .map(testProgressResource -> {
                                final var testProgressResult = testResultsMap.get(testProgressResource.getId()).stream()
                                        .map(testResultMapper::toResource)
                                        .findFirst()
                                        .orElse(null);
                                testProgressResource.setTestResult(testProgressResult);
                                return testProgressResource;
                            })
                            .toList();
                    resource.setTestProgresses(testProgresses);
                    return resource;
                })
                .toList();

        return StudentTestAssignationsResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(tests.size())
                .totalSize(testsPage.getTotalElements())
                .totalPages(testsPage.getTotalPages())
                .tests(testResources)
                .build();
    }

    @Override
    public TestResource prepareTestResource(@NonNull Test test) {
        populateTest(test);
        return testMapper.toResource(test);
    }
}
