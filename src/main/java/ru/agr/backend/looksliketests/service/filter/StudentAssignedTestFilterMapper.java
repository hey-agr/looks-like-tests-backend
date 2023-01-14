package ru.agr.backend.looksliketests.service.filter;

import lombok.NonNull;
import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.db.repository.filter.StudentAssignedTestSpecificationFilter;
import ru.agr.backend.looksliketests.db.repository.filter.StudentTestHistorySpecificationFilter;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface StudentAssignedTestFilterMapper {
    StudentAssignedTestSpecificationFilter toSpecificationFilter(@NonNull StudentAssignedTestFilter studentAssignedTestFilter);
}
