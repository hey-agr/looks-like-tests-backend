package ru.agr.backend.looksliketests.controller.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@AllArgsConstructor
@Data
public class StudentTestAssignationResource {
    Long testId;
    String name;
    String description;
    Long minCorrectAnswers;
    Long questionsCount;
    Long attempts;
    Long duration;
    Boolean isNeedVerify;
    List<TestProgressResource> testProgresses;
}
