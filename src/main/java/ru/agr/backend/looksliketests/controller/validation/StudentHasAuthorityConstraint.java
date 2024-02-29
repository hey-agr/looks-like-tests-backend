package ru.agr.backend.looksliketests.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.agr.backend.looksliketests.controller.validation.impl.StudentHasAuthorityValidator;

import java.lang.annotation.*;

/**
 * @author Arslan Rabadanov
 */
@Documented
@Constraint(validatedBy = StudentHasAuthorityValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentHasAuthorityConstraint {
    String message() default "Invalid studentId, user doesn't have role: STUDENT";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
