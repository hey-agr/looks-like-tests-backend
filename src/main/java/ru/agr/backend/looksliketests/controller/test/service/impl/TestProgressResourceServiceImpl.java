package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.controller.test.mapper.TestProgressMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestProgressResourceService;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestProgressResourceServiceImpl implements TestProgressResourceService {
    private final TestProgressMapper testProgressMapper;

    @Override
    public TestProgress prepareTestProgress(@NonNull Test test, @NonNull User user) {
        return TestProgress.builder()
                .test(test)
                .user(user)
                .dateStarted(ZonedDateTime.now())
                .build();
    }

    @Override
    public TestProgressResource prepareTestProgressResource(@NonNull TestProgress testProgress) {
        return testProgressMapper.toResource(testProgress);
    }
}
