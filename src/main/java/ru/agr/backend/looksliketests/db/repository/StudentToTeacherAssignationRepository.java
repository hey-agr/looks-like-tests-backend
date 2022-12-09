package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;

/**
 * @author Arslan Rabadanov
 */
public interface StudentToTeacherAssignationRepository extends JpaRepository<StudentToTeacherAssignation, Long> {
}
