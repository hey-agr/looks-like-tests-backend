package ru.agr.backend.looksliketests.db.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.repository.filter.TestProgressSpecificationFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
public class TestProgressSpecification implements Specification<TestProgress> {
    private final TestProgressSpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<TestProgress> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(filter)) {
            Optional.ofNullable(filter.getStudentIds())
                    .map(studentIds -> criteriaBuilder.in(root.get(TestProgress.Fields.user).get("id").in(studentIds)))
                    .ifPresent(predicates::add);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
