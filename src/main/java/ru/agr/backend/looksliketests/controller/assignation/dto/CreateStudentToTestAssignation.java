package ru.agr.backend.looksliketests.controller.assignation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class CreateStudentToTestAssignation {
    @NotNull Long studentId;
    @NotNull Long testId;
}
