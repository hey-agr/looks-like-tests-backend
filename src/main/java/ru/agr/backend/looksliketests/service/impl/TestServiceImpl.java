package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.db.repository.TestRepository;
import ru.agr.backend.looksliketests.db.repository.specification.TestSpecification;
import ru.agr.backend.looksliketests.service.OptionService;
import ru.agr.backend.looksliketests.service.QuestionService;
import ru.agr.backend.looksliketests.service.TestService;
import ru.agr.backend.looksliketests.service.filter.TestFilter;
import ru.agr.backend.looksliketests.service.filter.TestFilterMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final QuestionService questionService;
    private final TestFilterMapper testFilterMapper;
    private final OptionService optionService;

    @Override
    public Page<TestEntity> findPageable(@NonNull Pageable pageable) {
        return testRepository.findAll(pageable);
    }

    @Override
    public Page<TestEntity> findFiltered(@NonNull TestFilter testFilter, @NonNull Pageable pageable) {
        final var specificationFilter = testFilterMapper.toTestSpecificationFilter(testFilter);
        final var specification = new TestSpecification(specificationFilter);
        return testRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<TestEntity> findById(@NonNull Long id) {
        return testRepository.findById(id);
    }

    @Override
    @Transactional
    public TestEntity save(@NonNull TestEntity testEntity) {
        if (nonNull(testEntity.getQuestions())) {
            testEntity.setNeedVerification(questionService.hasWritingQuestions(testEntity.getQuestions()));
        }
        return testRepository.save(testEntity);
    }

    @Override
    public void populateTest(@NonNull TestEntity... testEntities) {
        final var testIds = Arrays.stream(testEntities)
                .map(TestEntity::getId)
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
        Arrays.stream(testEntities).forEach(test ->
                test.setQuestions(testQuestionsMap.getOrDefault(test.getId(), null)));
    }
}
