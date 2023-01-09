package ru.agr.backend.looksliketests.controller.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestProgressResource {
    private Long id;
    private Long userId;
    private Long testId;
    private ZonedDateTime dateStarted;
    private ZonedDateTime dateFinished;
    private TestResultResource testResult;
}
