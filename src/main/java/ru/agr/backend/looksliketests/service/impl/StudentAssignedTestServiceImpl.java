package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.StudentAssignedTest;
import ru.agr.backend.looksliketests.db.repository.StudentAssignedTestRepository;
import ru.agr.backend.looksliketests.db.repository.specification.StudentAssignedTestSpecification;
import ru.agr.backend.looksliketests.service.StudentAssignedTestService;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilter;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilterMapper;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class StudentAssignedTestServiceImpl implements StudentAssignedTestService {
    private final StudentAssignedTestRepository studentAssignedTestRepository;
    private final StudentAssignedTestFilterMapper studentAssignedTestFilterMapper;

    @Override
    public Page<StudentAssignedTest> findFiltered(@NonNull StudentAssignedTestFilter studentAssignedTestFilter, @NonNull Pageable pageable) {
        var specificationFilter = studentAssignedTestFilterMapper.toSpecificationFilter(studentAssignedTestFilter);
        var specification = new StudentAssignedTestSpecification(specificationFilter);
        return studentAssignedTestRepository.findAll(specification, pageable);
    }
}
