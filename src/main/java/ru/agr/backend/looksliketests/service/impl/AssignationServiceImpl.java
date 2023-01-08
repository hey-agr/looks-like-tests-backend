package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;
import ru.agr.backend.looksliketests.db.repository.StudentToTeacherAssignationRepository;
import ru.agr.backend.looksliketests.db.repository.StudentToTestAssignationRepository;
import ru.agr.backend.looksliketests.service.AssignationService;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class AssignationServiceImpl implements AssignationService {
    private final StudentToTeacherAssignationRepository studentToTeacherAssignationRepository;
    private final StudentToTestAssignationRepository studentToTestAssignationRepository;

    @Override
    public StudentToTeacherAssignation save(@NonNull StudentToTeacherAssignation studentToTeacherAssignation) {
        return studentToTeacherAssignationRepository.save(studentToTeacherAssignation);
    }

    @Override
    public StudentToTestAssignation save(@NonNull StudentToTestAssignation studentToTestAssignation) {
        return studentToTestAssignationRepository.save(studentToTestAssignation);
    }

    @Override
    public boolean isStudentAssignedToTest(@NonNull Long studentId, @NonNull Long testId) {
        return studentToTestAssignationRepository.findFirstByStudentIdAndTestId(studentId, testId).isPresent();
    }
}
