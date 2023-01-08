package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.TestResult;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
