package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = {TestProgressMapper.class, QuestionMapper.class, OptionMapper.class})

public abstract class TestAnswerMapper {
    public abstract TestAnswer toEntity(CreateTestAnswerDto createTestAnswerDto);
    public abstract TestAnswerResource toResource(TestAnswer testAnswer);
}
