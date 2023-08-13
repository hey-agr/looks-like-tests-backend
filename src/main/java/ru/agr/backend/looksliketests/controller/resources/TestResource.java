package ru.agr.backend.looksliketests.controller.resources;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
@ToString
public class TestResource {
    Long id;
    String name;
    String description;
    Long duration;
    Long minRightAnswers;
    Long attempts;
    Boolean isNeedVerify;
    List<QuestionResource> questions;
}
