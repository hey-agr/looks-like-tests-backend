package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.agr.backend.looksliketests.controller.resources.QuestionResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.db.entity.main.Question;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = OptionMapper.class)
public abstract class QuestionMapper {
    @Mappings({
            @Mapping(source = "options", target = "answers")
    })
    public abstract QuestionResource toQuestionResource(Question question);

    @Mappings({
            @Mapping(source = "answers", target = "options")
    })
    public abstract Question toEntity(CreateQuestionDto createQuestionDto);
}
