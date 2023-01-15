package ru.agr.backend.looksliketests.controller.test.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.db.repository.TestRepository;

/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class TestIdValidator implements ConstraintValidator<TestId, Long> {
    private final TestRepository testRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return testRepository.findById(value).isPresent();
    }
}
