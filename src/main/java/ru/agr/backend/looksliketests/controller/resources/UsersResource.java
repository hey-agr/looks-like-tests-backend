package ru.agr.backend.looksliketests.controller.resources;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class UsersResource {
    List<UserResource> users;
}
