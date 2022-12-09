package ru.agr.backend.looksliketests.db.repository.filter;

import lombok.Builder;
import lombok.Value;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class UserSpecificationFilter {
    Set<UserAuthority.AuthorityName> authorities;
}
