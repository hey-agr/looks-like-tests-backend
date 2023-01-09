package ru.agr.backend.looksliketests.db.repository.filter;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class StudentTestHistorySpecificationFilter {
    List<Long> studentIds;
}
