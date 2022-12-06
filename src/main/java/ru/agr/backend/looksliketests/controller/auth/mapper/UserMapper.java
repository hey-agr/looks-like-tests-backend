package ru.agr.backend.looksliketests.controller.auth.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = AuthorityMapper.class)
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
        user.setAuthorities(userCreateDto.authorities().stream()
                .map(authorityName ->
                        UserAuthority.builder()
                                .user(user)
                                .name(UserAuthority.AuthorityName.valueOf(authorityName.name()))
                                .build())
                .collect(Collectors.toList())
        );
    }

    public abstract UserResource toUserResource(User user);
}
