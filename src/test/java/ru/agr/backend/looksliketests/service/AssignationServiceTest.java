package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;
import ru.agr.backend.looksliketests.db.repository.StudentToTeacherAssignationRepository;
import ru.agr.backend.looksliketests.db.repository.StudentToTestAssignationRepository;
import ru.agr.backend.looksliketests.service.impl.AssignationServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class AssignationServiceTest {
    private static final Long STUDENT_ID = 1L;
    private static final Long TEACHER_ID = 2L;
    private static final Long TEST_ID = 2L;
    private static final Long ID = 3L;
    @Mock
    private StudentToTeacherAssignationRepository studentToTeacherAssignationRepository;
    @Mock
    private StudentToTestAssignationRepository studentToTestAssignationRepository;

    @InjectMocks
    private AssignationServiceImpl service;

    @Test
    void testSaveStudentToTeacherAssignation() {
        var givenStudentToTeacherAssignation = StudentToTeacherAssignation.builder()
                .studentId(STUDENT_ID)
                .teacherId(TEACHER_ID)
                .build();

        var expectedStudentToTeacherAssignation = StudentToTeacherAssignation.builder()
                .studentId(STUDENT_ID)
                .teacherId(TEACHER_ID)
                .id(ID)
                .dateCreated(LocalDateTime.now())
                .build();

        when(studentToTeacherAssignationRepository.save(givenStudentToTeacherAssignation))
                .thenReturn(expectedStudentToTeacherAssignation);

        assertEquals(expectedStudentToTeacherAssignation, service.save(givenStudentToTeacherAssignation));

        verify(studentToTeacherAssignationRepository, times(1))
                .save(givenStudentToTeacherAssignation);
    }

    @Test
    void testSaveStudentToTestAssignationRepository() {
        var givenStudentToTestAssignation= StudentToTestAssignation.builder()
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .build();

        var expectedStudentToTestAssignation = StudentToTestAssignation.builder()
                .studentId(STUDENT_ID)
                .testId(TEST_ID)
                .id(ID)
                .dateCreated(LocalDateTime.now())
                .build();

        when(studentToTestAssignationRepository.save(givenStudentToTestAssignation))
                .thenReturn(expectedStudentToTestAssignation);

        assertEquals(expectedStudentToTestAssignation, service.save(givenStudentToTestAssignation));

        verify(studentToTestAssignationRepository, times(1))
                .save(givenStudentToTestAssignation);
    }

    @Test
    void studentIsAssignedToTest() {
        when(studentToTestAssignationRepository.findFirstByStudentIdAndTestId(STUDENT_ID, TEST_ID))
                .thenReturn(Optional.ofNullable(StudentToTestAssignation.builder().build()));

        assertTrue(service.isStudentAssignedToTest(STUDENT_ID, TEST_ID));
    }

    @Test
    void studentIsNotAssignedToTest() {
        when(studentToTestAssignationRepository.findFirstByStudentIdAndTestId(STUDENT_ID, TEST_ID))
                .thenReturn(Optional.empty());

        assertFalse(service.isStudentAssignedToTest(STUDENT_ID, TEST_ID));
    }
}