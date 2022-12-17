package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.controller.test.mapper.TestAnswerMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestAnswerResourceService;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestAnswerResourceServiceImpl implements TestAnswerResourceService {
    private final TestAnswerMapper testAnswerMapper;

    @Override
    public TestAnswerResource toResource(@NonNull TestAnswer testAnswer) {
        return testAnswerMapper.toResource(testAnswer);
    }
}
