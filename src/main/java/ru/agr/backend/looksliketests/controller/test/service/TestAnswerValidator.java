package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestAnswerValidationException;
import ru.agr.backend.looksliketests.db.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
public interface TestAnswerValidator {
    void validate(@NonNull Test test, @NonNull CreateTestAnswerDto createTestAnswerDto) throws TestAnswerValidationException;
}
