package ru.agr.backend.looksliketests.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.agr.backend.looksliketests.controller.validation.impl.TeacherHasAuthorityValidator;

import java.lang.annotation.*;

/**
 * @author Arslan Rabadanov
 */
@Documented
@Constraint(validatedBy = TeacherHasAuthorityValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TeacherHasAuthorityConstraint {
    String message() default "Invalid teacherId, user doesn't have role: TEACHER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
