package ru.agr.backend.looksliketests.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.agr.backend.looksliketests.controller.validation.impl.TestIdValidator;

import java.lang.annotation.*;

/**
 * @author Arslan Rabadanov
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TestIdValidator.class)
public @interface TestId {
    String message() default "TestId is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
