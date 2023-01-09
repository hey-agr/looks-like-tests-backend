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
public class StudentTestHistoryResource {
    Long testId;
    String name;
    String description;
    Long testProgressId;
    ZonedDateTime dateStarted;
    ZonedDateTime dateFinished;
    Long questionCount;
    Long rightAnswersCount;
    Long pendingAnswersCount;
    Long wrongAnswersCount;
    TestResultResource.TestResultStatusResource testResultStatus;
}
