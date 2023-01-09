package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilter;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface StudentTestHistoryService {
    Page<StudentTestHistory> findFiltered(@NonNull StudentTestHistoryFilter studentTestHistoryFilter, @NonNull Pageable pageable);
}
