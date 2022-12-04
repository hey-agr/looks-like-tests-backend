package ru.agr.backend.looksliketests.controller.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Arslan Rabadanov
 */
public record UserCreateDto(
        @NotEmpty String username,
        @NotEmpty String password,
        String firstName,
        String lastName,
        @Email String email) {
}
