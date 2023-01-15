package ru.agr.backend.looksliketests.db.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;
import ru.agr.backend.looksliketests.db.repository.filter.StudentTestHistorySpecificationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
@Builder
@RequiredArgsConstructor
public class StudentTestHistorySpecification implements Specification<StudentTestHistory> {
    private final StudentTestHistorySpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<StudentTestHistory> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(filter)) {
            Optional.ofNullable(filter.getStudentIds())
                    .map(studentIds -> root.get("userId").in(studentIds))
                    .ifPresent(predicates::add);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
