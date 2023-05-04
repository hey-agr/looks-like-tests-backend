package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.StudentAssignedTest;
import ru.agr.backend.looksliketests.db.repository.StudentAssignedTestRepository;
import ru.agr.backend.looksliketests.db.repository.filter.StudentAssignedTestSpecificationFilter;
import ru.agr.backend.looksliketests.db.repository.specification.StudentAssignedTestSpecification;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilter;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilterMapper;
import ru.agr.backend.looksliketests.service.impl.StudentAssignedTestServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class StudentAssignedTestServiceTest {
    private static final Long STUDENT_ID1 = 1L;
    private static final Long STUDENT_ID2 = 2L;
    private static final Long TEST_ID1 = 3L;
    private static final Long TEST_ID2 = 4L;

    @Mock
    private StudentAssignedTestRepository studentAssignedTestRepository;
    @Mock
    private StudentAssignedTestFilterMapper studentAssignedTestFilterMapper;

    @InjectMocks
    private StudentAssignedTestServiceImpl service;

    private StudentAssignedTestFilter givenStudentAssignedTestFilter;
    private StudentAssignedTestSpecificationFilter givenStudentAssignedTestSpecificationFilter;
    private StudentAssignedTestSpecification studentAssignedTestSpecification;
    private Pageable givenPageable;

    @BeforeEach
    void setUp() {
        givenStudentAssignedTestFilter = StudentAssignedTestFilter.builder()
                .isActual(Boolean.TRUE)
                .studentIds(List.of(STUDENT_ID1, STUDENT_ID2))
                .build();

        givenStudentAssignedTestSpecificationFilter = StudentAssignedTestSpecificationFilter.builder()
                .isActual(Boolean.TRUE)
                .studentIds(List.of(STUDENT_ID1, STUDENT_ID2))
                .build();

        studentAssignedTestSpecification = new StudentAssignedTestSpecification(givenStudentAssignedTestSpecificationFilter);

        lenient().when(studentAssignedTestFilterMapper.toSpecificationFilter(givenStudentAssignedTestFilter))
                .thenReturn(givenStudentAssignedTestSpecificationFilter);

        givenPageable = Pageable.unpaged();
    }

    @Test
    void findFiltered() {
        var expectedContent = List.of(
                StudentAssignedTest.builder()
                        .testId(TEST_ID1)
                        .studentId(STUDENT_ID1)
                        .build(),
                StudentAssignedTest.builder()
                        .testId(TEST_ID2)
                        .studentId(STUDENT_ID2)
                        .build()
        );

        var expectedPage = new PageImpl<>(expectedContent);

        when(studentAssignedTestRepository.findAll(studentAssignedTestSpecification, givenPageable))
                .thenReturn(expectedPage);

        assertEquals(expectedPage, service.findFiltered(givenStudentAssignedTestFilter, givenPageable));
    }

    @Test
    void findFilteredNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(null, null));
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(givenStudentAssignedTestFilter, null));
    }
}