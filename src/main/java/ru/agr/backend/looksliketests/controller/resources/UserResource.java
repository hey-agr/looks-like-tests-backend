package ru.agr.backend.looksliketests.controller.resources;

import lombok.*;
import ru.agr.backend.looksliketests.controller.auth.dto.UserAuthorityName;

import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserResource {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private boolean activated;
    private Set<UserAuthorityName> authorities;
}
