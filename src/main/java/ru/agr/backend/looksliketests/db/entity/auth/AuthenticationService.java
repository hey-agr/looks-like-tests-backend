package ru.agr.backend.looksliketests.db.entity.auth;

import lombok.NonNull;

/**
 * @author Arslan Rabadanov
 */
public interface AuthenticationService {
    User authenticate(@NonNull String username, @NonNull String password);
}
