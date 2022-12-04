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
    String username;
    String firstName;
    String lastName;
    String email;
    boolean activated;
    Set<AuthorityResource> authorities;
}
