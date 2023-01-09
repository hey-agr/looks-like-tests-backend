package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;
import ru.agr.backend.looksliketests.db.repository.StudentTestHistoryRepository;
import ru.agr.backend.looksliketests.db.repository.specification.StudentTestHistorySpecification;
import ru.agr.backend.looksliketests.service.StudentTestHistoryService;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilter;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilterMapper;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Service
@RequiredArgsConstructor
public class StudentTestHistoryServiceImpl implements StudentTestHistoryService {
    private final StudentTestHistoryFilterMapper studentTestHistoryFilterMapper;
    private final StudentTestHistoryRepository studentTestHistoryRepository;

    @Override
    public Page<StudentTestHistory> findFiltered(@NonNull StudentTestHistoryFilter studentTestHistoryFilter, @NonNull Pageable pageable) {
        final var specificationFilter = studentTestHistoryFilterMapper.toSpecificationFilter(studentTestHistoryFilter);
        final var specification = StudentTestHistorySpecification.builder()
                .filter(specificationFilter)
                .build();
        return studentTestHistoryRepository.findAll(specification, pageable);
    }
}
