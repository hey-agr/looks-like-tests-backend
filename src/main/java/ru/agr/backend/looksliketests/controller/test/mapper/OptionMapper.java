package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.dto.OptionResource;
import ru.agr.backend.looksliketests.db.entity.main.Option;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public abstract class OptionMapper {
    public abstract OptionResource toOptionResource(Option option);
}
