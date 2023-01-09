package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.resources.QuestionImageResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionImageDto;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface QuestionImageMapper {
    QuestionImage toEntity(CreateQuestionImageDto createQuestionImageDto);
    QuestionImageResource toResource(QuestionImage questionImage);
}
