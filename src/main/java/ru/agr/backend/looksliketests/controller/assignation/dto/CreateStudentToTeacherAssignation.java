package ru.agr.backend.looksliketests.controller.assignation.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@Jacksonized
public class CreateStudentToTeacherAssignation {
    @NotNull Long studentId;
    @NotNull Long teacherId;
}
