package ru.agr.backend.looksliketests.controller.test.mapper;

import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.db.entity.main.StudentAssignedTest;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;

import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public abstract class TestMapper {
    @Mapping(source = "minCorrectAnswers", target = "minRightAnswers")
    @Mapping(source = "needVerification", target = "isNeedVerify")
    public abstract TestResource toResource(TestEntity testEntity);

    @Mapping(source = "minRightAnswers", target = "minCorrectAnswers")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "needVerification", constant = "false")
    public abstract TestEntity toEntity(CreateTestDto createTestDto);

    @AfterMapping
    protected void toEntityAfterMapping(@MappingTarget TestEntity testEntity) {
        if (nonNull(testEntity.getQuestions()) && Hibernate.isInitialized(testEntity.getQuestions())) {
            testEntity.getQuestions().forEach(question -> {
                question.setTest(testEntity);
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

    @Mapping(source = "needVerification", target = "isNeedVerify")
    @Mapping(target = "testProgresses", ignore = true)
    public abstract StudentTestAssignationResource toStudentTestAssignedResource(@NotNull StudentAssignedTest studentAssignedTest);
}
