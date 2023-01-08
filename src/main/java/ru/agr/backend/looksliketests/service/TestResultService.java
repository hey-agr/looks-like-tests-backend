package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.entity.main.TestResult;

/**
 * @author Arslan Rabadanov
 */
public interface TestResultService {
    TestResult save(@NonNull TestResult testResult);
    TestResult calculateTestResult(@NonNull TestProgress testProgress);
}
