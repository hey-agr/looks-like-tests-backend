package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.repository.OptionRepository;
import ru.agr.backend.looksliketests.service.impl.OptionServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class OptionServiceTest {
    private static final Set<Long> QUESTION_IDS = Set.of(1L, 2L, 3L);

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionServiceImpl service;

    @Test
    void findByQuestionIds() {
        var expectedOptions = List.of(Option.builder().build());

        when(optionRepository.findAllByQuestionIdIn(QUESTION_IDS))
                .thenReturn(expectedOptions);

        assertEquals(expectedOptions, service.findByQuestionIds(QUESTION_IDS));
    }

    @Test
    void findByQuestionIdsEmptyList() {
        assertEquals(Collections.emptyList(), service.findByQuestionIds(Collections.emptySet()));

        verify(optionRepository, never())
                .findAllByQuestionIdIn(anySet());
    }

    @Test
    void findByQuestionIdsEmptyNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findByQuestionIds(null));
    }
}