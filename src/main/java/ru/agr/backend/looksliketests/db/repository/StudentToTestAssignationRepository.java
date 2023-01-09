package ru.agr.backend.looksliketests.db.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface StudentToTestAssignationRepository extends JpaRepository<StudentToTestAssignation, Long> {
    Optional<StudentToTestAssignation> findFirstByStudentIdAndTestId(@NonNull Long studentId, @NonNull Long testId);
}
