package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestValidationException;

/**
 * @author Arslan Rabadanov
 */
public interface TestValidationService {
    void validate(@NonNull CreateTestDto createTestDto) throws TestValidationException;
}
