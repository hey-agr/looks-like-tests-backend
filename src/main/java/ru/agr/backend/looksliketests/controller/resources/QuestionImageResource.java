package ru.agr.backend.looksliketests.controller.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionImageResource {
    Long id;
    String url;
    Long questionId;
}
