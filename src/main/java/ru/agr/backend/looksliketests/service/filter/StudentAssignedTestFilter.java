package ru.agr.backend.looksliketests.service.filter;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Value
@Builder
public class StudentAssignedTestFilter implements Serializable {
    List<Long> studentIds;
    Boolean isActual;
}
