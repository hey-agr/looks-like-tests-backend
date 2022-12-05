package ru.agr.backend.looksliketests.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.repository.auth.AuthorityRepository;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

}
