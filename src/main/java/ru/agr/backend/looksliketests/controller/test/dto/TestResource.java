package ru.agr.backend.looksliketests.controller.test.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.agr.backend.looksliketests.controller.dto.QuestionResource;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
public class TestResource {
    Long id;
    String name;
    String description;
    String duration;
    Integer minRightAnswers;
    Integer attempts;
    Boolean isNeedVerify;
    List<QuestionResource> questions;
}
