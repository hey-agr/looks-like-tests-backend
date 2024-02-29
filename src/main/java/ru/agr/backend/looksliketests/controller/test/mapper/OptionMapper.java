package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.resources.OptionResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateOptionDto;
import ru.agr.backend.looksliketests.db.entity.main.Option;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface OptionMapper {
    OptionResource toOptionResource(Option option);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    Option toEntity(CreateOptionDto createOptionDto);
}
