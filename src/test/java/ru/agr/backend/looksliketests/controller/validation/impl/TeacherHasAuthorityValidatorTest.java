package ru.agr.backend.looksliketests.controller.validation.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.service.auth.UserService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TeacherHasAuthorityValidatorTest {
    private static final Long TEACHER_ID = 123L;
    private static final Long WRONG_TEACHER_ID = 321L;
    @Mock
    private UserService userService;

    @InjectMocks
    private TeacherHasAuthorityValidator validator;

    @Test
    void isValid() {
        when(userService.checkIfUsersAuthorityExists(TEACHER_ID, UserAuthority.AuthorityName.TEACHER))
                .thenReturn(true);
        assertTrue(validator.isValid(TEACHER_ID, null));
    }

    @Test
    void isNotValid() {
        when(userService.checkIfUsersAuthorityExists(WRONG_TEACHER_ID, UserAuthority.AuthorityName.TEACHER))
                .thenReturn(false);
        assertFalse(validator.isValid(WRONG_TEACHER_ID, null));
    }
}