package ru.agr.backend.looksliketests.controller.auth.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.auth.dto.AuthorityResource;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:41:39+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class AuthorityMapperImpl extends AuthorityMapper {

    @Override
    public AuthorityResource toAuthorityResource(UserAuthority userAuthority) {
        if ( userAuthority == null ) {
            return null;
        }

        AuthorityResource.AuthorityResourceBuilder authorityResource = AuthorityResource.builder();

        if ( userAuthority.getName() != null ) {
            authorityResource.name( userAuthority.getName().name() );
        }

        return authorityResource.build();
    }
}
