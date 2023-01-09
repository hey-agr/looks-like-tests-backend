package ru.agr.backend.looksliketests.controller.test.mapper;

import lombok.NonNull;
import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.resources.StudentTestHistoryResource;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface StudentTestHistoryMapper {
    StudentTestHistoryResource toResource(@NonNull StudentTestHistory studentTestHistory);
}
