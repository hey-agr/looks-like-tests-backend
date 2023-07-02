package ru.agr.backend.looksliketests.controller.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.validation.TestId;
import ru.agr.backend.looksliketests.service.TestService;

/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class TestIdValidator implements ConstraintValidator<TestId, Long> {
    private final TestService testService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return testService.findById(value)
                .isPresent();
    }
}
