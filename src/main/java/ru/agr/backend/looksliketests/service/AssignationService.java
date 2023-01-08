package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;

/**
 * @author Arslan Rabadanov
 */
public interface AssignationService {
    StudentToTeacherAssignation save(@NonNull StudentToTeacherAssignation studentToTeacherAssignation);
    StudentToTestAssignation save(@NonNull StudentToTestAssignation studentToTestAssignation);
    boolean isStudentAssignedToTest(@NonNull Long studentId, @NonNull Long testId);
}
