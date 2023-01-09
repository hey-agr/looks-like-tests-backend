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
public class TestResultResource {
    Long testProgressId;
    Long questionCount;
    Long rightAnswersCount;
    Long pendingAnswersCount;
    Long wrongAnswersCount;
    Boolean expired;
    TestResultStatusResource testResultStatus;

    public enum TestResultStatusResource {
        IN_PROGRESS,
        PASSED,
        PENDING,
        FAILED;
    }
}
