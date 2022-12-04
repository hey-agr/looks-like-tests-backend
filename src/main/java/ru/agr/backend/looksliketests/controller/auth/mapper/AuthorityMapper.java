package ru.agr.backend.looksliketests.controller.auth.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.auth.dto.AuthorityResource;
import ru.agr.backend.looksliketests.db.entity.auth.Authority;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public abstract class AuthorityMapper {
    public abstract AuthorityResource toAuthorityResource(Authority authority);
}
