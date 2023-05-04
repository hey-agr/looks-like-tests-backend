package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.db.repository.TestAnswerRepository;
import ru.agr.backend.looksliketests.service.impl.TestAnswerServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestAnswerServiceTest {
    private final static Long TEST_PROGRESS_ID = 1L;

    @Mock
    private TestAnswerRepository testAnswerRepository;

    @InjectMocks
    private TestAnswerServiceImpl service;


    @Test
    void save() {
        var givenTestAnswer = TestAnswer.builder()
                .build();

        when(testAnswerRepository.save(givenTestAnswer))
                .thenReturn(givenTestAnswer);

        assertEquals(givenTestAnswer, service.save(givenTestAnswer));
    }

    @Test
    void saveNpe() {
        assertThrows(NullPointerException.class,
                () -> service.save(null));
    }

    @Test
    void saveAll() {
        var givenTestAnswer = TestAnswer.builder()
                .build();
        var givenSecondTestAnswer = TestAnswer.builder()
                .build();
        var givenAnswers = List.of(givenTestAnswer, givenSecondTestAnswer);

        when(testAnswerRepository.saveAll(givenAnswers))
                .thenReturn(givenAnswers);

        assertEquals(givenAnswers, service.saveAll(givenAnswers));

    }

    @Test
    void saveAllNpe() {
        assertThrows(NullPointerException.class,
                () -> service.saveAll(null));
    }

    @Test
    void findByTestProgressId() {
        var givenTestAnswer = TestAnswer.builder()
                .build();
        var givenSecondTestAnswer = TestAnswer.builder()
                .build();
        var expectedAnswers = List.of(givenTestAnswer, givenSecondTestAnswer);

        when(testAnswerRepository.findAllByTestProgressId(TEST_PROGRESS_ID))
                .thenReturn(expectedAnswers);

        assertEquals(expectedAnswers, service.findByTestProgressId(TEST_PROGRESS_ID));
    }
}