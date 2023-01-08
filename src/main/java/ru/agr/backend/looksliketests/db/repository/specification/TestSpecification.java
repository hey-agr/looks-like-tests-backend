package ru.agr.backend.looksliketests.db.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.repository.filter.TestSpecificationFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
public class TestSpecification implements Specification<Test> {
    private final TestSpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<Test> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filter)) {
            Optional.ofNullable(filter.getStudentId())
                    .ifPresent(studentId -> {
                        var subQuery = query.subquery(StudentToTestAssignation.class);
                        var subQueryFrom = subQuery.from(StudentToTestAssignation.class);
                        subQuery.select(subQueryFrom.get("testId")).where(subQueryFrom.get("studentId").in(studentId));
                        predicates.add(criteriaBuilder.in(root.get(Test.Fields.id)).value(subQuery));
                    });
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
