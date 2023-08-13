package ru.agr.backend.looksliketests.controller.test.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationResource;
import ru.agr.backend.looksliketests.controller.resources.TestCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.resources.TestResultResource;
import ru.agr.backend.looksliketests.controller.test.mapper.StudentTestHistoryMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestProgressMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestResultMapper;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.*;
import ru.agr.backend.looksliketests.service.TestProgressService;
import ru.agr.backend.looksliketests.service.TestResultService;
import ru.agr.backend.looksliketests.service.TestService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestResourceServiceImplTest {
    private static final Long TEST_ID = 123L;
    private static final Long TEST_PROGRESS_ID = 7437L;
    private static final Long STUDENT_ID = 94883L;
    private static final Long TEACHER_ID = 8432842L;
    private static final Long ASSIGNMENT_ID = 58483L;

    @Mock
    private TestMapper testMapper;
    @Mock
    private TestProgressService testProgressService;
    @Mock
    private TestResultService testResultService;
    @Mock
    private TestResultMapper testResultMapper;
    @Mock
    private TestProgressMapper testProgressMapper;
    @Mock
    private StudentTestHistoryMapper studentTestHistoryMapper;
    @Mock
    private TestService testService;

    @InjectMocks
    private TestResourceServiceImpl service;

    private TestEntity test;

    @BeforeEach
    void init() {
        test = TestEntity.builder()
                .id(TEST_ID)
                .minCorrectAnswers(2L)
                .needVerification(true)
                .name("SUPER TEST")
                .description("SOME DESCRIPTION")
                .duration(3600L)
                .attempts(3L)
                .minCorrectAnswers(5L)
                .build();
    }

    @Test
    void prepareTestsResource() {
        var pageTest = new PageImpl<>(List.of(test));
        var pageable = Pageable.ofSize(1);
        var expectedSize = 1;
        var expectedPage = 0;
        var totalPages = 1;

        var expectedTestResource = TestResource.builder()
                .id(test.getId())
                .minRightAnswers(test.getMinCorrectAnswers())
                .isNeedVerify(test.getNeedVerification())
                .name(test.getName())
                .description(test.getDescription())
                .duration(test.getDuration())
                .attempts(test.getAttempts())
                .build();

        var expectedTestResources = TestCollectionResource.builder()
                .pageNumber(expectedPage)
                .pageSize(expectedSize)
                .size(expectedSize)
                .totalSize((long) expectedSize)
                .totalPages(totalPages)
                .tests(List.of(expectedTestResource))
                .build();

        when(testMapper.toResource(test))
                .thenReturn(expectedTestResource);

        assertEquals(expectedTestResources,
                service.prepareTestsResource(pageTest, pageable));

        verify(testService, times(1))
                .populateTest(test);
    }

    @Test
    void prepareTestsResourceNPE() {
        assertThrows(NullPointerException.class,
                () -> service.prepareTestsResource(null, null));
        var page = new PageImpl<TestEntity>(Collections.emptyList());
        assertThrows(NullPointerException.class,
                () -> service.prepareTestsResource(page, null));
    }

    @Test
    void prepareStudentTestAssignationsResource() {
        var user = User.builder()
                .id(STUDENT_ID)
                .build();
        var studentTestAssignment = StudentAssignedTest.builder()
                .id(ASSIGNMENT_ID)
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .attempts(test.getAttempts())
                .name(test.getName())
                .description(test.getDescription())
                .duration(test.getDuration())
                .needVerification(test.getNeedVerification())
                .failedCount(0L)
                .attemptsCount(1L)
                .build();
        var page = new PageImpl<>(List.of(studentTestAssignment));
        var pageable = Pageable.ofSize(1);
        var testProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(test)
                .user(user)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .dateFinished(LocalDateTime.now().atZone(ZoneId.systemDefault()).plusMinutes(5))
                .build();
        var testResult = TestResult.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .questionCount(3L)
                .rightAnswersCount(3L)
                .wrongAnswersCount(0L)
                .pendingAnswersCount(0L)
                .expired(false)
                .testResultStatus(TestResultStatus.PASSED)
                .build();

        when(testProgressService.findAllByUserIdAndTestIds(STUDENT_ID, TEST_ID))
                .thenReturn(List.of(testProgress));
        when(testResultService.findByTestProgressIds(TEST_PROGRESS_ID))
                .thenReturn(List.of(testResult));

        var expectedTestProgressResource = TestProgressResource.builder()
                .id(TEST_PROGRESS_ID)
                .testId(TEST_ID)
                .userId(testProgress.getUser().getId())
                .dateStarted(testProgress.getDateStarted())
                .dateFinished(testProgress.getDateFinished())
                .build();

        when(testProgressMapper.toResource(testProgress))
                .thenReturn(expectedTestProgressResource);

        var expectedStudentTestAssignedResource = StudentTestAssignationResource.builder()
                .testId(TEST_ID)
                .attempts(test.getAttempts())
                .name(test.getName())
                .description(test.getDescription())
                .duration(test.getDuration())
                .isNeedVerify(test.getNeedVerification())
                .testProgresses(List.of(expectedTestProgressResource))
                .minCorrectAnswers(test.getMinCorrectAnswers())
                .questionsCount(testResult.getQuestionCount())
                .build();

        when(testMapper.toStudentTestAssignedResource(studentTestAssignment))
                .thenReturn(expectedStudentTestAssignedResource);

        var expectedTestResult = TestResultResource.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .testResultStatus(TestResultResource.TestResultStatusResource.PASSED)
                .expired(testResult.getExpired())
                .questionCount(testResult.getQuestionCount())
                .rightAnswersCount(testResult.getRightAnswersCount())
                .wrongAnswersCount(testResult.getWrongAnswersCount())
                .pendingAnswersCount(testResult.getPendingAnswersCount())
                .build();

        when(testResultMapper.toResource(testResult))
                .thenReturn(expectedTestResult);

        var expectedStudentTestAssignationCollectionResource = StudentTestAssignationCollectionResource.builder()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .size(1)
                .totalSize(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .tests(List.of(expectedStudentTestAssignedResource))
                .build();

        assertEquals(expectedStudentTestAssignationCollectionResource,
                service.prepareStudentTestAssignationsResource(user, page, pageable));
    }

    @Test
    void prepareStudentTestAssignationsResourceNPE() {
        assertThrows(NullPointerException.class,
                () -> service.prepareStudentTestAssignationsResource(null, null, null));
        var user = User.builder().build();
        assertThrows(NullPointerException.class,
                () -> service.prepareStudentTestAssignationsResource(user, null, null));
        var page = new PageImpl<StudentAssignedTest>(Collections.emptyList());
        assertThrows(NullPointerException.class,
                () -> service.prepareStudentTestAssignationsResource(user, page, null));
    }

    @Test
    void prepareStudentTestHistoryCollectionResource() {
    }

    @Test
    void prepareTestResource() {
    }
}