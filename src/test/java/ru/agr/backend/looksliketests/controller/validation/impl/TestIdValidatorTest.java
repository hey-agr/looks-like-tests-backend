package ru.agr.backend.looksliketests.controller.validation.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.service.TestService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestIdValidatorTest {
    private static final Long TEST_ID = 1L;
    private static final Long WRONG_TEST_ID = 2L;
    private static final ru.agr.backend.looksliketests.db.entity.main.Test TEST =
            ru.agr.backend.looksliketests.db.entity.main.Test.builder()
                    .id(TEST_ID)
                    .build();

    @Mock
    private TestService testService;

    @InjectMocks
    private TestIdValidator validator;

    @Test
    void isValid() {
        when(testService.findById(TEST_ID))
                .thenReturn(Optional.of(TEST));
        assertTrue(validator.isValid(TEST_ID, null));
    }

    @Test
    void isNotValid() {
        when(testService.findById(WRONG_TEST_ID))
                .thenReturn(Optional.empty());
        assertFalse(validator.isValid(WRONG_TEST_ID, null));
    }
}