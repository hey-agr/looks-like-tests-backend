package ru.agr.backend.looksliketests.controller.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.validation.TeacherHasAuthorityConstraint;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.service.auth.UserService;


/**
 * @author Arslan Rabadanov
 */
@Component
@RequiredArgsConstructor
public class TeacherHasAuthorityValidator implements ConstraintValidator<TeacherHasAuthorityConstraint, Long> {
    private final UserService userService;

    @Override
    public boolean isValid(Long teacherId, ConstraintValidatorContext context) {
        return userService.checkIfUsersAuthorityExists(teacherId, UserAuthority.AuthorityName.TEACHER);
    }
}
