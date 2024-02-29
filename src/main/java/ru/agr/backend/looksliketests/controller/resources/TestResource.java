package ru.agr.backend.looksliketests.controller.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
@ToString
@Jacksonized
@AllArgsConstructor
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
