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
class StudentHasAuthorityValidatorTest {
    private static final Long STUDENT_ID = 857L;
    private static final Long WRONG_STUDENT_ID = 3947L;
    @Mock
    private UserService userService;

    @InjectMocks
    private StudentHasAuthorityValidator validator;

    @Test
    void isValid() {
        when(userService.checkIfUsersAuthorityExists(STUDENT_ID, UserAuthority.AuthorityName.STUDENT))
                .thenReturn(true);
        assertTrue(validator.isValid(STUDENT_ID, null));
    }

    @Test
    void isNotValid() {
        when(userService.checkIfUsersAuthorityExists(WRONG_STUDENT_ID, UserAuthority.AuthorityName.STUDENT))
                .thenReturn(false);
        assertFalse(validator.isValid(WRONG_STUDENT_ID, null));
    }
}