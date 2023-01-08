package ru.agr.backend.looksliketests.db.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    @Query("SELECT ta FROM TestAnswer ta WHERE ta.testProgress.id = :testProgressId")
    List<TestAnswer> findAllByTestProgressId(@NonNull @Param("testProgressId") Long testProgressId);
}
