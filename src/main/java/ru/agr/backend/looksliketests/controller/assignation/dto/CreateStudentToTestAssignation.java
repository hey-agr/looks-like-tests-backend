package ru.agr.backend.looksliketests.controller.assignation.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import jakarta.validation.constraints.NotNull;

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
