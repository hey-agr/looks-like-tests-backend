package ru.agr.backend.looksliketests.controller.auth.dto;

import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
public record UserCreateDto(
        @NotEmpty String username,
        @NotEmpty String password,
        String firstName,
        String lastName,
        String middleName,
        @Email String email,
        String phone,
        @NotNull Set<UserAuthority.AuthorityName> authorities) {
}
