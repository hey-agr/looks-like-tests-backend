package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface StudentTestHistoryRepository extends JpaRepository<StudentTestHistory, Long>, JpaSpecificationExecutor<StudentTestHistory> {
}
