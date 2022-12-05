package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Test> findPageable(@NonNull Pageable pageable) {
        return testRepository.findAll(pageable);
    }

    @Override
    public Optional<Test> findById(@NonNull Long id) {
        return testRepository.findById(id);
    }

    @Override
    @Transactional
    public Test save(@NonNull Test test) {
        return testRepository.save(test);
    }
}
