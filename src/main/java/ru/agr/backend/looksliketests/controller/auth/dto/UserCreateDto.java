package ru.agr.backend.looksliketests.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
        @NotNull Set<UserCreateAuthorityName> authorities) {
}
