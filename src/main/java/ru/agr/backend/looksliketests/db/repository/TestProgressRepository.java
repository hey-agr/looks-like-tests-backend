package ru.agr.backend.looksliketests.db.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface TestProgressRepository extends JpaRepository<TestProgress, Long> {
    @Query("SELECT tp FROM TestProgress tp WHERE tp.test.id = :testId AND tp.user.id = :userId")
    List<TestProgress> findAllByTestIdAndUserId(@NonNull @Param("testId") Long testId, @NonNull @Param("userId") Long userId);
}
