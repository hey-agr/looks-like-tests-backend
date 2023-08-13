package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.service.filter.TestFilter;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface TestService {
    Page<TestEntity> findPageable(@NonNull Pageable pageable);
    Page<TestEntity> findFiltered(@NonNull TestFilter testFilter, @NonNull Pageable pageable);
    Optional<TestEntity> findById(@NonNull Long id);
    TestEntity save(@NonNull TestEntity testEntity);
    void populateTest(@NonNull TestEntity... testEntities);
}
