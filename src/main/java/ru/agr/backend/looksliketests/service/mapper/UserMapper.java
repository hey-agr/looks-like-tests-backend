package ru.agr.backend.looksliketests.service.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.entity.auth.User;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings(
            @Mapping(target = "password", ignore = true)
    )
    public abstract User toEntity(UserCreateDto userCreateDto);

    @AfterMapping
    protected void toEntityAfterMapping(UserCreateDto userCreateDto, @MappingTarget User user) {
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));
    }
}
