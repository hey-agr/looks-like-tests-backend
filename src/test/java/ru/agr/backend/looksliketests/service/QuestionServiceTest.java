package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.repository.QuestionRepository;
import ru.agr.backend.looksliketests.service.impl.QuestionServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    private static final Long TEST_ID1 = 1L;
    private static final Long TEST_ID2 = 2L;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl service;

    @Test
    void findByTestIds() {
        var expectedQuestions = List.of(
                Question.builder().build(),
                Question.builder().build()
        );

        when(questionRepository.findAllByTestIdIn(TEST_ID1, TEST_ID2))
                .thenReturn(expectedQuestions);

        assertEquals(expectedQuestions, service.findByTestIds(TEST_ID1, TEST_ID2));
    }

    @Test
    void findByTestIdsNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findByTestIds(null));
    }

    @Test
    void hasWritingQuestionsTrue() {
        var givenQuestions = List.of(
                Question.builder()
                        .type(QuestionType.WRITING)
                        .build(),
                Question.builder()
                        .type(QuestionType.OPTIONS)
                        .build(),
                Question.builder()
                        .type(QuestionType.OPTIONS_MULTIPLY)
                        .build()
        );

        assertTrue(service.hasWritingQuestions(givenQuestions));
    }

    @Test
    void hasWritingQuestionsFalse() {
        var givenQuestions = List.of(
                Question.builder()
                        .type(QuestionType.OPTIONS)
                        .build(),
                Question.builder()
                        .type(QuestionType.OPTIONS_MULTIPLY)
                        .build()
        );

        assertFalse(service.hasWritingQuestions(givenQuestions));
    }

    @Test
    void hasWritingQuestionsNPE() {
        assertThrows(NullPointerException.class,
                () -> service.hasWritingQuestions(null));
    }
}