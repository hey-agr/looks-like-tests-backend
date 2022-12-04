package ru.agr.backend.looksliketests.controller.auth.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.auth.dto.AuthorityResource;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserResource;
import ru.agr.backend.looksliketests.db.entity.auth.Authority;
import ru.agr.backend.looksliketests.db.entity.auth.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-04T19:47:56+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        if ( userCreateDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userCreateDto.username() );
        user.setFirstName( userCreateDto.firstName() );
        user.setLastName( userCreateDto.lastName() );
        user.setEmail( userCreateDto.email() );

        toEntityAfterMapping( userCreateDto, user );

        return user;
    }

    @Override
    public UserResource toUserResource(User user) {
        if ( user == null ) {
            return null;
        }

        UserResource.UserResourceBuilder userResource = UserResource.builder();

        userResource.username( user.getUsername() );
        userResource.firstName( user.getFirstName() );
        userResource.lastName( user.getLastName() );
        userResource.email( user.getEmail() );
        userResource.activated( user.isActivated() );
        userResource.authorities( authoritySetToAuthorityResourceSet( user.getAuthorities() ) );

        return userResource.build();
    }

    protected Set<AuthorityResource> authoritySetToAuthorityResourceSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityResource> set1 = new LinkedHashSet<AuthorityResource>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityMapper.toAuthorityResource( authority ) );
        }

        return set1;
    }
}
