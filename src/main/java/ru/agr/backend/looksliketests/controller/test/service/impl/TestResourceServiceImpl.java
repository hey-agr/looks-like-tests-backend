package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.service.OptionService;
import ru.agr.backend.looksliketests.service.QuestionService;

import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class TestResourceServiceImpl implements TestResourceService {
    private final QuestionService questionService;
    private final OptionService optionService;

    @Override
    public void populateTest(Test test) {
        final var testQuestions = questionService.findByTestId(test.getId());
        final var questionIds = testQuestions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
        final var questionOptionsMap = optionService.findByQuestionIds(questionIds).stream()
                .collect(Collectors.groupingBy(option -> option.getQuestion().getId()));
        testQuestions.forEach(testQuestion ->
                testQuestion.setOptions(questionOptionsMap.getOrDefault(testQuestion.getId(), null)));
        test.setQuestions(testQuestions);
    }
}
