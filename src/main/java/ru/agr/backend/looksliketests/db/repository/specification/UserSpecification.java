package ru.agr.backend.looksliketests.db.repository.specification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.db.repository.filter.UserSpecificationFilter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {
    private final UserSpecificationFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filter)) {
            Optional.ofNullable(filter.getAuthorities())
                    .ifPresent(authorityNames -> {
                        var subQuery = query.subquery(UserAuthority.class);
                        var subQueryFrom = subQuery.from(UserAuthority.class);
                        subQuery.select(subQueryFrom.get("user").get("id")).where(subQueryFrom.get("name").in(authorityNames));
                        predicates.add(builder.in(root.get(User.Fields.id)).value(subQuery));
                    });
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
