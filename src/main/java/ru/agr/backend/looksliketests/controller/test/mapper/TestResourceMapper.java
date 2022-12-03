package ru.agr.backend.looksliketests.controller.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.agr.backend.looksliketests.controller.test.TestResource;
import ru.agr.backend.looksliketests.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface TestResourceMapper {
    @Mappings({
            @Mapping(source = "minCorrectAnswers", target = "minRightAnswers"),
            @Mapping(source = "needVerification", target = "isNeedVerify")
    })
    TestResource toResource(Test test);
}
