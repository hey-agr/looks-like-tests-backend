package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswersDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestProgressValidationException;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

/**
 * @author Arslan Rabadanov
 */
public interface TestProgressValidationService {
    void validate(@NonNull TestProgress testProgress, @NonNull CreateTestAnswersDto createTestAnswersDto) throws TestProgressValidationException;
}
