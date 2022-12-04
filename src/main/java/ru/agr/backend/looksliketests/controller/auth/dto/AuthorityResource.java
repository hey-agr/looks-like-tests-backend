package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Builder;
import lombok.Value;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class AuthorityResource {
    String name;
}
