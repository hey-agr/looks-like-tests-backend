package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.StudentTestHistoryCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.test.mapper.StudentTestHistoryMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestProgressMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestResultMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.*;
import ru.agr.backend.looksliketests.service.TestProgressService;
import ru.agr.backend.looksliketests.service.TestResultService;
import ru.agr.backend.looksliketests.service.TestService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class TestResourceServiceImpl implements TestResourceService {
    private final TestMapper testMapper;
    private final TestProgressService testProgressService;
    private final TestResultService testResultService;
    private final TestResultMapper testResultMapper;
    private final TestProgressMapper testProgressMapper;
    private final StudentTestHistoryMapper studentTestHistoryMapper;
    private final TestService testService;

    @Override
    public TestCollectionResource prepareTestsResource(@NonNull Page<TestEntity> testsPage, @NonNull Pageable pageable) {
        final var tests = testsPage.getContent();
        testService.populateTest(tests.toArray(new TestEntity[0]));
        return TestCollectionResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(tests.size())
                .totalSize(testsPage.getTotalElements())
                .totalPages(testsPage.getTotalPages())
                .tests(tests.stream().map(testMapper::toResource).toList())
                .build();
    }

    @Override
    public StudentTestAssignationCollectionResource prepareStudentTestAssignationsResource(@NonNull User user,
                                                                                           @NonNull Page<StudentAssignedTest> testsPage,
                                                                                           @NonNull Pageable pageable) {
        final var tests = testsPage.getContent();
        final var testIds = tests.stream()
                .map(StudentAssignedTest::getTestId)
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
                .toList();
        testResources.forEach(resource -> {
            final var testProgresses = testProgressesMap.getOrDefault(resource.getTestId(), Collections.emptyList()).stream()
                    .map(testProgressMapper::toResource)
                    .toList();
            testProgresses.forEach(testProgressResource -> {
                final var testProgressResult = testResultsMap.getOrDefault(testProgressResource.getId(), Collections.emptyList()).stream()
                        .map(testResultMapper::toResource)
                        .findFirst()
                        .orElse(null);
                testProgressResource.setTestResult(testProgressResult);
            });
            resource.setTestProgresses(testProgresses);
        });

        return StudentTestAssignationCollectionResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(tests.size())
                .totalSize(testsPage.getTotalElements())
                .totalPages(testsPage.getTotalPages())
                .tests(testResources)
                .build();
    }

    @Override
    public StudentTestHistoryCollectionResource prepareStudentTestHistoryCollectionResource(@NonNull Page<StudentTestHistory> studentTestHistoryPage, @NonNull Pageable pageable) {
        final var tests = studentTestHistoryPage.getContent();

        return StudentTestHistoryCollectionResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(tests.size())
                .totalSize(studentTestHistoryPage.getTotalElements())
                .totalPages(studentTestHistoryPage.getTotalPages())
                .tests(tests.stream().map(studentTestHistoryMapper::toResource).toList())
                .build();
    }

    @Override
    public TestResource prepareTestResource(@NonNull TestEntity testEntity) {
        testService.populateTest(testEntity);
        return testMapper.toResource(testEntity);
    }
}
