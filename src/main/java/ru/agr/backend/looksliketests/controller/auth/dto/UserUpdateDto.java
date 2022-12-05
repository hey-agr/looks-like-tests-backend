package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;


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
