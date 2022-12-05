package ru.agr.backend.looksliketests.controller.auth.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.auth.dto.AuthorityResource;
import ru.agr.backend.looksliketests.controller.auth.dto.UserAuthorityName;
import ru.agr.backend.looksliketests.controller.auth.dto.UserCreateDto;
import ru.agr.backend.looksliketests.controller.auth.dto.UserResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:41:39+0100",
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
        user.setMiddleName( userCreateDto.middleName() );
        user.setEmail( userCreateDto.email() );
        user.setPhone( userCreateDto.phone() );
        user.setAuthorities( userAuthorityNameSetToUserAuthorityList( userCreateDto.authorities() ) );

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
        userResource.middleName( user.getMiddleName() );
        userResource.email( user.getEmail() );
        userResource.phone( user.getPhone() );
        userResource.activated( user.isActivated() );
        userResource.authorities( userAuthorityListToAuthorityResourceSet( user.getAuthorities() ) );

        return userResource.build();
    }

    protected UserAuthority userAuthorityNameToUserAuthority(UserAuthorityName userAuthorityName) {
        if ( userAuthorityName == null ) {
            return null;
        }

        UserAuthority.UserAuthorityBuilder userAuthority = UserAuthority.builder();

        return userAuthority.build();
    }

    protected List<UserAuthority> userAuthorityNameSetToUserAuthorityList(Set<UserAuthorityName> set) {
        if ( set == null ) {
            return null;
        }

        List<UserAuthority> list = new ArrayList<UserAuthority>( set.size() );
        for ( UserAuthorityName userAuthorityName : set ) {
            list.add( userAuthorityNameToUserAuthority( userAuthorityName ) );
        }

        return list;
    }

    protected Set<AuthorityResource> userAuthorityListToAuthorityResourceSet(List<UserAuthority> list) {
        if ( list == null ) {
            return null;
        }

        Set<AuthorityResource> set = new LinkedHashSet<AuthorityResource>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( UserAuthority userAuthority : list ) {
            set.add( authorityMapper.toAuthorityResource( userAuthority ) );
        }

        return set;
    }
}
