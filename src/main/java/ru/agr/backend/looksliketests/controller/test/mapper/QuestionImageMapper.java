package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.resources.QuestionImageResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionImageDto;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface QuestionImageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    QuestionImage toEntity(CreateQuestionImageDto createQuestionImageDto);

    @Mapping(target = "questionId", source = "question.id")
    QuestionImageResource toResource(QuestionImage questionImage);
}
