package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.resources.QuestionResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.db.entity.main.Question;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = {OptionMapper.class, QuestionImageMapper.class})
public interface QuestionMapper {
    @Mapping(source = "options", target = "answers")
    QuestionResource toQuestionResource(Question question);

    @Mapping(source = "answers", target = "options")
    Question toEntity(CreateQuestionDto createQuestionDto);
}
