package ru.agr.backend.looksliketests.db.repository.filter;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class StudentAssignedTestSpecificationFilter implements Serializable {
    List<Long> studentIds;
    Boolean isActual;
}
