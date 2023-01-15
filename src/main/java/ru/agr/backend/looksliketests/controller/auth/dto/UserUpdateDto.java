package ru.agr.backend.looksliketests.controller.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class UserUpdateDto {
    String password;
    String firstName;
    String lastName;
    String middleName;
    @Email String email;
    String phone;
}
