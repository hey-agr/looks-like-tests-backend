package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;

/**
 * @author Arslan Rabadanov
 */
public interface StudentToTestAssignationRepository extends JpaRepository<StudentToTestAssignation, Long> {
}
