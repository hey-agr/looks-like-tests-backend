package ru.agr.backend.looksliketests.controller.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.validation.StudentHasAuthorityConstraint;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.service.auth.UserService;


/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class StudentHasAuthorityValidator implements ConstraintValidator<StudentHasAuthorityConstraint, Long> {
    private final UserService userService;

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext context) {
        return userService.checkIfUsersAuthorityExists(studentId, UserAuthority.AuthorityName.STUDENT);
    }
}
