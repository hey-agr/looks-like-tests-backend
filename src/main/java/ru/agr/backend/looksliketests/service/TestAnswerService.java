package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

/**
 * @author Arslan Rabadanov
 */
public interface TestAnswerService {
    TestAnswer save(@NonNull TestAnswer testAnswer);
}
