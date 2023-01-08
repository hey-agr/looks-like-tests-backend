package ru.agr.backend.looksliketests.service.filter;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class TestFilter {
    List<Long> studentId;
}
