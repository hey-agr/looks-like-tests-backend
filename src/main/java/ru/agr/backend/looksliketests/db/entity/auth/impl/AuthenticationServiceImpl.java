package ru.agr.backend.looksliketests.db.entity.auth.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.db.entity.auth.AuthenticationService;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.service.auth.UserService;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User authenticate(@NonNull String username, @NonNull String password) throws AuthenticationException{
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User '"+username+"' not found"));
        if (!passwordEncoder.encode(user.getPassword()).equals(password)) {
            throw new BadCredentialsException("User '"+username+"' provided wrong password");
        }
        return user;
    }
}
