package ru.agr.backend.looksliketests.controller.auth.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.auth.dto.UserUpdateDto;
import ru.agr.backend.looksliketests.db.entity.auth.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:41:39+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class UserMergerMapperImpl extends UserMergerMapper {

    @Override
    public User toEntity(UserUpdateDto userUpdateDto, User user) {
        if ( userUpdateDto == null ) {
            return user;
        }

        if ( userUpdateDto.getFirstName() != null ) {
            user.setFirstName( userUpdateDto.getFirstName() );
        }
        if ( userUpdateDto.getLastName() != null ) {
            user.setLastName( userUpdateDto.getLastName() );
        }
        if ( userUpdateDto.getMiddleName() != null ) {
            user.setMiddleName( userUpdateDto.getMiddleName() );
        }
        if ( userUpdateDto.getEmail() != null ) {
            user.setEmail( userUpdateDto.getEmail() );
        }
        if ( userUpdateDto.getPhone() != null ) {
            user.setPhone( userUpdateDto.getPhone() );
        }

        toEntityAfterMapping( userUpdateDto, user );

        return user;
    }
}
