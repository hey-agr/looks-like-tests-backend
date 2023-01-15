package ru.agr.backend.looksliketests.db.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.main.StudentAssignedTest;
import ru.agr.backend.looksliketests.db.repository.filter.StudentAssignedTestSpecificationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
public class StudentAssignedTestSpecification implements Specification<StudentAssignedTest> {
    private final StudentAssignedTestSpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<StudentAssignedTest> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filter)) {
            Optional.ofNullable(filter.getStudentIds())
                    .map(studentIds -> root.get(StudentAssignedTest.Fields.studentId).in(studentIds))
                    .ifPresent(predicates::add);
            Optional.ofNullable(filter.getIsActual())
                    .ifPresent(isActual -> {
                        if (isActual) {
                            var attemptsPredicate = criteriaBuilder.greaterThan(
                                    root.get(StudentAssignedTest.Fields.attempts),
                                    root.get(StudentAssignedTest.Fields.attemptsCount));
                            var passedPredicate = criteriaBuilder.equal(
                                    root.get(StudentAssignedTest.Fields.passedCount),
                                    0);
                            predicates.add(criteriaBuilder.and(attemptsPredicate, passedPredicate));
                        } else {
                            var attemptsPredicate = criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(StudentAssignedTest.Fields.attemptsCount),
                                    root.get(StudentAssignedTest.Fields.attempts));
                            var passedPredicate = criteriaBuilder.greaterThan(
                                    root.get(StudentAssignedTest.Fields.passedCount),
                                    1);
                            predicates.add(criteriaBuilder.or(attemptsPredicate, passedPredicate));
                        }
                    });
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
