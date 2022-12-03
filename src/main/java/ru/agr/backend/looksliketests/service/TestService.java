package ru.agr.backend.looksliketests.service;

import ru.agr.backend.looksliketests.entity.main.Test;

import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface TestService {
    Optional<Test> findById(Long id);
    Test save(Test test);
}
