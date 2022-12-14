package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.service.filter.TestFilter;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface TestService {
    Page<Test> findPageable(@NonNull Pageable pageable);
    Page<Test> findFiltered(@NonNull TestFilter testFilter, @NonNull Pageable pageable);
    Optional<Test> findById(@NonNull Long id);
    Test save(@NonNull Test test);
}
