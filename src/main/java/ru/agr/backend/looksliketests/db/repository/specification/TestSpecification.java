package ru.agr.backend.looksliketests.db.repository.specification;

import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.db.repository.filter.TestSpecificationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class TestSpecification implements Specification<TestEntity> {
    private final TestSpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<TestEntity> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filter)) {
            Optional.ofNullable(filter.getStudentIds())
                    .ifPresent(studentIds -> {
                        var subQuery = query.subquery(StudentToTestAssignation.class);
                        var subQueryFrom = subQuery.from(StudentToTestAssignation.class);
                        subQuery.select(subQueryFrom.get("testId")).where(subQueryFrom.get("studentId").in(studentIds));
                        predicates.add(criteriaBuilder.in(root.get(TestEntity.Fields.id)).value(subQuery));
                    });
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
