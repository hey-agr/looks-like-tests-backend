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
    Integer questionCount;
    Integer rightAnswersCount;
    Boolean expired;
    TestResultStatusResource testResultStatus;

    public enum TestResultStatusResource {
        PASSED,
        PENDING,
        FAILED;
    }
}
