package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class UserResource {
    Long id;
    String username;
    String firstName;
    String lastName;
    String middleName;
    String email;
    String phone;
    boolean activated;
    Set<AuthorityResource> authorities;
}
