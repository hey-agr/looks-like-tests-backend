package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.repository.TestProgressRepository;
import ru.agr.backend.looksliketests.service.TestProgressService;
import ru.agr.backend.looksliketests.service.TestResultService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestProgressServiceImpl implements TestProgressService {
    private final TestProgressRepository testProgressRepository;
    private final TestResultService testResultService;

    @Override
    public TestProgress save(@NonNull TestProgress testProgress) {
        return testProgressRepository.save(testProgress);
    }

    @Override
    public Optional<TestProgress> findById(@NonNull Long id) {
        return testProgressRepository.findById(id);
    }

    @Override
    public List<TestProgress> findAllByTestIdAndUserId(@NonNull Long testId, @NonNull Long userId) {
        return testProgressRepository.findAllByTestIdAndUserId(testId, userId);
    }

    @Override
    @Transactional
    public TestProgress finishProgress(@NonNull TestProgress testProgress) {
        final var testDateFinished = ZonedDateTime.now();
        testProgress.setDateFinished(testDateFinished);
        return save(testProgress);
    }

    @Override
    public boolean isFinished(@NonNull TestProgress testProgress) {
        return Objects.nonNull(testProgress.getDateFinished());
    }
}
