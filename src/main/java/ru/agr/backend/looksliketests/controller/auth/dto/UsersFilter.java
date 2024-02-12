package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
@Builder
public class UsersFilter {
    Set<UserAuthorityName> authorities;
}
