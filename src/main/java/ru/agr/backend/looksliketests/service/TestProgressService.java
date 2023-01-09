package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.util.List;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface TestProgressService {
    TestProgress save(@NonNull TestProgress testProgress);
    Optional<TestProgress> findById(@NonNull Long id);
    List<TestProgress> findAllByUserIdAndTestIds(@NonNull Long userId, @NonNull Long... testId);
    TestProgress finishProgress(@NonNull TestProgress testProgress);
    boolean isFinished(@NonNull TestProgress testProgress);
}
