package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Value
@Jacksonized
public class UsersFilter {
    Set<UserAuthorityName> authorities;
}
