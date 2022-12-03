package ru.agr.backend.looksliketests.controller.test;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.controller.dto.QuestionResource;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
public class TestResource {
    private final Long id;
    private final String name;
    private final String description;
    private final String duration;
    private final Integer minRightAnswers;
    private final Integer attempts;
    private final Boolean isNeedVerify;
    private final List<QuestionResource> questions;
}
