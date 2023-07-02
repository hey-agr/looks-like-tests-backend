package ru.agr.backend.looksliketests.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.agr.backend.looksliketests.controller.validation.impl.TeacherHasAuthorityValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Arslan Rabadanov
 */
@Documented
@Constraint(validatedBy = TeacherHasAuthorityValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TeacherHasAuthorityConstraint {
    String message() default "Invalid teacherId, the found user doesn't have role: TEACHER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
