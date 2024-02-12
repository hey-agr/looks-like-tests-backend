package ru.agr.backend.looksliketests.controller.auth.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.agr.backend.looksliketests.controller.auth.dto.UserAuthorityName;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.resources.UserResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = AuthorityMapper.class)
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    public abstract User toEntity(UserCreateDto userCreateDto);

    @AfterMapping
    protected void toEntityAfterMapping(UserCreateDto userCreateDto, @MappingTarget User.UserBuilder user) {
        user.password(passwordEncoder.encode(userCreateDto.password()));
        user.authorities(userCreateDto.authorities().stream()
                .map(authorityName ->
                        UserAuthority.builder()
                                .name(UserAuthority.AuthorityName.valueOf(authorityName.name()))
                                .build())
                .toList()
        );
    }

    @Mappings(
            @Mapping(target = "authorities", ignore = true)
    )
    public abstract UserResource toUserResource(User user);

    @AfterMapping
    protected void toUserResourceAfterMapping(User user, @MappingTarget UserResource.UserResourceBuilder userResourceBuilder) {
        if (nonNull(user.getAuthorities()) && Hibernate.isInitialized(user.getAuthorities())) {
            userResourceBuilder.authorities(user.getAuthorities().stream()
                    .map(userAuthority -> UserAuthorityName.valueOf(userAuthority.getName().name()))
                    .collect(Collectors.toSet()));
        }
    }
}
