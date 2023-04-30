package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;
import ru.agr.backend.looksliketests.db.repository.StudentTestHistoryRepository;
import ru.agr.backend.looksliketests.db.repository.filter.StudentTestHistorySpecificationFilter;
import ru.agr.backend.looksliketests.db.repository.specification.StudentTestHistorySpecification;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilter;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilterMapper;
import ru.agr.backend.looksliketests.service.impl.StudentTestHistoryServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class StudentTestHistoryServiceTest {
    private static final Long STUDENT_ID1 = 1L;
    private static final Long STUDENT_ID2 = 2L;

    @Mock
    private StudentTestHistoryFilterMapper studentTestHistoryFilterMapper;
    @Mock
    private StudentTestHistoryRepository studentTestHistoryRepository;

    @InjectMocks
    private StudentTestHistoryServiceImpl service;

    private StudentTestHistoryFilter givenFilter;
    private StudentTestHistorySpecificationFilter givenSpecificationFilter;
    private StudentTestHistorySpecification givenSpecification;
    private Pageable givenPageable;

    @BeforeEach
    void setUp() {
        givenFilter = StudentTestHistoryFilter.builder()
                .studentIds(List.of(STUDENT_ID1, STUDENT_ID2))
                .build();

        givenSpecificationFilter = StudentTestHistorySpecificationFilter.builder()
                .studentIds(List.of(STUDENT_ID1, STUDENT_ID2))
                .build();

        lenient().when(studentTestHistoryFilterMapper.toSpecificationFilter(givenFilter))
                .thenReturn(givenSpecificationFilter);

        givenSpecification = new StudentTestHistorySpecification(givenSpecificationFilter);
        givenPageable = Pageable.unpaged();
    }

    @Test
    void findFiltered() {
        var expectedContent = List.of(
                StudentTestHistory.builder()
                        .userId(STUDENT_ID1)
                        .build(),
                StudentTestHistory.builder()
                        .userId(STUDENT_ID2)
                        .build()
        );

        var expectedPage = new PageImpl<>(expectedContent);

        when(studentTestHistoryRepository.findAll(givenSpecification, givenPageable))
                .thenReturn(expectedPage);

        assertEquals(expectedPage, service.findFiltered(givenFilter, givenPageable));
    }

    @Test
    void findFilteredNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(null, null));
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(givenFilter, null));
    }
}