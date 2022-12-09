package ru.agr.backend.looksliketests.controller.resources;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
public class QuestionResource {
    Long id;
    String name;
    String type;
    List<OptionResource> answers;
}