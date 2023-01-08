package ru.agr.backend.looksliketests.service.filter;

import lombok.NonNull;
import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.db.repository.filter.TestSpecificationFilter;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface TestFilterMapper {
    TestSpecificationFilter toTestSpecificationFilter(@NonNull TestFilter testFilter);
}
