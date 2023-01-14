package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.StudentAssignedTest;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilter;

/**
 * @author Arslan Rabadanov
 */
public interface StudentAssignedTestService {
    Page<StudentAssignedTest> findFiltered(@NonNull StudentAssignedTestFilter studentAssignedTestFilter, @NonNull Pageable pageable);
}
