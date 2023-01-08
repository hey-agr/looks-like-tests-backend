package ru.agr.backend.looksliketests.controller.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResource {
    Long id;
    String name;
    QuestionTypeResource type;
    List<OptionResource> answers;
}