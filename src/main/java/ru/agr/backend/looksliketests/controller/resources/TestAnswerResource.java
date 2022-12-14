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
public class TestAnswerResource {
    Long id;
    Long testProgressId;
    QuestionResource question;
    Long optionId;
    String textAnswer;
}
