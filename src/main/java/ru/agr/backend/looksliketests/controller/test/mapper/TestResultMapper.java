package ru.agr.backend.looksliketests.controller.test.mapper;

import lombok.NonNull;
import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.resources.TestResultResource;
import ru.agr.backend.looksliketests.db.entity.main.TestResult;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = TestProgressMapper.class)
public abstract class TestResultMapper {
    public abstract TestResultResource toResource(@NonNull TestResult testResult);
}
