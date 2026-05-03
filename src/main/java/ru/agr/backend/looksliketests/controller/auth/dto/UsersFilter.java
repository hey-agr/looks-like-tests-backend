package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Data
@Jacksonized
@Builder
public class UsersFilter {
    Set<UserAuthorityName> authorities;
}
