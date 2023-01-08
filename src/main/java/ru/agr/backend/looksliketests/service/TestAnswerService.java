package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface TestAnswerService {
    TestAnswer save(@NonNull TestAnswer testAnswer);
    List<TestAnswer> saveAll(@NonNull List<TestAnswer> testAnswers);
    List<TestAnswer> findByTestProgressId(@NonNull Long testProgressId);
}
