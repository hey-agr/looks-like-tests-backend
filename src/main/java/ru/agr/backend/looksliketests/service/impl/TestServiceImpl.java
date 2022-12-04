package ru.agr.backend.looksliketests.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.repository.TestRepository;
import ru.agr.backend.looksliketests.service.TestService;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    @Transactional
    public Test save(Test test) {
        return testRepository.save(test);
    }
}
