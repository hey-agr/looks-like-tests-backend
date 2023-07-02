package ru.agr.backend.looksliketests.controller.assignation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.controller.validation.StudentHasAuthorityConstraint;
import ru.agr.backend.looksliketests.controller.validation.TestId;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class CreateStudentToTestAssignation {
    @NotNull @StudentHasAuthorityConstraint
    Long studentId;
    @NotNull @TestId
    Long testId;
}
