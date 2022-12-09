package ru.agr.backend.looksliketests.controller.resources;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
public class StudentToTeacherAssignationResource {
    Long id;
    Long studentId;
    Long teacherId;
}
