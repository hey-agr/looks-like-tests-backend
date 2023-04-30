package ru.agr.backend.looksliketests.controller.auth.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.auth.dto.AuthorityResource;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityResource toAuthorityResource(UserAuthority userAuthority);
}
