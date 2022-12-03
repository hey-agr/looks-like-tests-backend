package ru.agr.backend.looksliketests.service.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.entity.auth.User;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class UserMergerMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "password", ignore = true)
    })
    public abstract User toEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

    @AfterMapping
    protected void toEntityAfterMapping(UserUpdateDto userUpdateDto, @MappingTarget User user) {
        if (nonNull(userUpdateDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
    }
}
