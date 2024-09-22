package ru.agr.backend.looksliketests.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;
import ru.agr.backend.looksliketests.db.repository.QuestionImageRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionImageServiceImplTest {
    private static final Long QUESTION_ID1 = 1L;
    private static final Long QUESTION_ID2 = 2L;

    @Mock
    private QuestionImageRepository questionImageRepository;

    @InjectMocks
    private QuestionImageServiceImpl service;

    @Test
    void findByQuestionIds() {
        var givenQuestionIds = Set.of(
                QUESTION_ID1, QUESTION_ID2
        );

        var expectedQuestionImages = List.of(
                QuestionImage.builder().build(),
                QuestionImage.builder().build()
        );

        when(questionImageRepository.findAllByQuestionIdIn(givenQuestionIds))
                .thenReturn(expectedQuestionImages);

        service.findByQuestionIds(givenQuestionIds);
    }

    @Test
    void findByQuestionIdsEmptyList() {
        Set<Long> givenQuestionIds = Collections.emptySet();
        assertEquals(Collections.emptyList(),
                service.findByQuestionIds(givenQuestionIds));
        verify(questionImageRepository, never())
                .findAllByQuestionIdIn(any());
    }

    @Test
    void testNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> service.findByQuestionIds(null));
    }
}