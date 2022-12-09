package ru.agr.backend.looksliketests.controller.test.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.*;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.db.entity.main.Test;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public abstract class TestMapper {
    @Mappings({
            @Mapping(source = "minCorrectAnswers", target = "minRightAnswers"),
            @Mapping(source = "needVerification", target = "isNeedVerify")
    })
    public abstract TestResource toResource(Test test);

    @Mappings({
            @Mapping(source = "minRightAnswers", target = "minCorrectAnswers")
    })
    public abstract Test toEntity(CreateTestDto createTestDto);

    @AfterMapping
    protected void toEntityAfterMapping(@MappingTarget Test test) {
        if (nonNull(test.getQuestions()) && Hibernate.isInitialized(test.getQuestions())) {
            test.getQuestions().forEach(question -> {
                question.setTest(test);
                if (nonNull(question.getOptions())
                        && Hibernate.isInitialized(question.getOptions())) {
                    question.getOptions().forEach(option -> option.setQuestion(question));
                }
                if (nonNull(question.getImages())
                        && Hibernate.isInitialized(question.getImages())) {
                    question.getImages().forEach(image -> image.setQuestion(question));
                }
            });
        }
    }
}
