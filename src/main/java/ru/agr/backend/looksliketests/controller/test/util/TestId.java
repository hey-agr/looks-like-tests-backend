package ru.agr.backend.looksliketests.controller.test.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
