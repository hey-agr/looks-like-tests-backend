package ru.agr.backend.looksliketests.controller.auth.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.auth.dto.UsersFilter;
import ru.agr.backend.looksliketests.db.repository.filter.UserSpecificationFilter;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface UserFilterMapper {
    UserSpecificationFilter toSpecificationFilter(UsersFilter usersFilter);
}
