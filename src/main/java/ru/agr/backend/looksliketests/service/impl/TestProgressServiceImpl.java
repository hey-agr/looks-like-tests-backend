package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.repository.TestProgressRepository;
import ru.agr.backend.looksliketests.service.TestProgressService;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestProgressServiceImpl implements TestProgressService {
    private final TestProgressRepository testProgressRepository;

    @Override
    public TestProgress save(@NonNull TestProgress testProgress) {
        return testProgressRepository.save(testProgress);
    }

    @Override
    public Optional<TestProgress> findById(@NonNull Long id) {
        return testProgressRepository.findById(id);
    }
}
