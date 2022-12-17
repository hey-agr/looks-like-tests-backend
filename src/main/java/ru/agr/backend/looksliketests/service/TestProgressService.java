package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface TestProgressService {
    TestProgress save(@NonNull TestProgress testProgress);
    Optional<TestProgress> findById(@NonNull Long id);
}
