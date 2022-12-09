package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.resources.TestsResource;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.service.OptionService;
import ru.agr.backend.looksliketests.service.QuestionService;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class TestResourceServiceImpl implements TestResourceService {
    private final QuestionService questionService;
    private final OptionService optionService;
    private final TestMapper testMapper;

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
                .tests(tests.stream().map(testMapper::toResource).toList())
                .build();
    }

    @Override
    public TestResource prepareTestResource(@NonNull Test test) {
        populateTest(test);
        return testMapper.toResource(test);
    }
}
