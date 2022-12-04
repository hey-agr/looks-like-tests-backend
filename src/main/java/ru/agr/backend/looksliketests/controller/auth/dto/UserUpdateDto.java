package ru.agr.backend.looksliketests.controller.auth.dto;

import javax.validation.constraints.Email;
import lombok.Data;


/**
 * @author Arslan Rabadanov
 */
@Data
public class UserUpdateDto {
    private final String password;
    private final String firstName;
    private final String lastName;
    @Email
    private final String email;
    private final Boolean activated;
}
