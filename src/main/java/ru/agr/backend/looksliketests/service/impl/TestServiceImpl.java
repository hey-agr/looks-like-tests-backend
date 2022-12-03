package ru.agr.backend.looksliketests.service.impl;

import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.entity.main.Test;
import ru.agr.backend.looksliketests.repository.TestRepository;
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
    public Test save(Test test) {
        return testRepository.save(test);
    }
}
