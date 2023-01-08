package ru.agr.backend.looksliketests.controller.test.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestAnswerValidationException;
import ru.agr.backend.looksliketests.controller.test.service.TestAnswerValidator;
import ru.agr.backend.looksliketests.db.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
@Component
public class TestAnswerValidatorImpl implements TestAnswerValidator {
    @Override
    public void validate(@NonNull Test test, @NonNull CreateTestAnswerDto createTestAnswerDto) throws TestAnswerValidationException {

    }
}
