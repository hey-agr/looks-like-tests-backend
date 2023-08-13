package ru.agr.backend.looksliketests.controller.test.mapper;

import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public abstract class TestAnswerMapper {
    public List<TestAnswer> toEntity(@NonNull TestProgress testProgress, @NonNull CreateTestAnswerDto createTestAnswerDto) {
        final var resultAnswers = new ArrayList<TestAnswer>();
        if (isNull(testProgress.getTest())
                || isNull(testProgress.getTest().getQuestions())
                || testProgress.getTest().getQuestions().isEmpty()) {
            return resultAnswers;
        }
        final var test = testProgress.getTest();
        var questionOpt = test.getQuestions().stream()
                .filter(q -> q.getId().equals(createTestAnswerDto.getQuestionId()))
                .findFirst();
        if (questionOpt.isPresent()) {
            final var question = questionOpt.get();
            if (nonNull(question.getType()) && question.getType().isOption()) {
                for (Long optionId : createTestAnswerDto.getOptionIds()) {
                    final var optionOpt = question.getOptions().stream()
                            .filter(opt -> opt.getId().equals(optionId))
                            .findFirst();
                    if (optionOpt.isPresent()) {
                        var testAnswer = TestAnswer.builder()
                                .testProgress(testProgress)
                                .question(question)
                                .option(optionOpt.get())
                                .build();
                        resultAnswers.add(testAnswer);
                    }
                }
            } else if (QuestionType.WRITING == question.getType()) {
                var testAnswer = TestAnswer.builder()
                        .testProgress(testProgress)
                        .textAnswer(createTestAnswerDto.getTextAnswer())
                        .question(question)
                        .build();
                resultAnswers.add(testAnswer);
            }
        }
        return resultAnswers;
    }

    public abstract TestAnswerResource toResource(TestAnswer testAnswer);

    @AfterMapping
    protected void toResourceAfterMapping(TestAnswer testAnswer, @MappingTarget TestAnswerResource.TestAnswerResourceBuilder testAnswerResourceBuilder) {
        testAnswerResourceBuilder.testProgressId(testAnswer.getTestProgress().getId());
        if (nonNull(testAnswer.getOption())) {
            testAnswerResourceBuilder.optionId(testAnswer.getOption().getId());
        }
    }
}
