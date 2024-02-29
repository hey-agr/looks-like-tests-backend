package ru.agr.backend.looksliketests.controller.test.mapper;

import org.junit.jupiter.api.Test;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswerDto;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAnswerMapperTest {
    private static final Long TEST_PROGRESS_ID = 765L;
    private static final Long OPTION_ID = 8573L;

    private final TestAnswerMapper testAnswerMapper = new TestAnswerMapperImpl();

    @Test
    void toEntityEmptyTest() {
        var givenTestProgress = TestProgress.builder().build();
        var givenCreateTestAnswerDto = CreateTestAnswerDto.builder().build();
        assertEquals(Collections.emptyList(),
                testAnswerMapper.toEntity(givenTestProgress, givenCreateTestAnswerDto));
    }

    @Test
    void toEntityEmptyTestQuestions() {
        var givenTest = TestEntity.builder()
                .build();
        var givenTestProgress = TestProgress.builder()
                .test(givenTest)
                .build();
        var givenCreateTestAnswerDto = CreateTestAnswerDto.builder().build();
        assertEquals(Collections.emptyList(),
                testAnswerMapper.toEntity(givenTestProgress, givenCreateTestAnswerDto));
    }

    @Test
    void toResourceAfterMapping() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .build();
        var givenOption = Option.builder()
                .id(OPTION_ID)
                .build();
        var givenTestAnswer = TestAnswer.builder()
                .testProgress(givenTestProgress)
                .option(givenOption)
                .build();

        var givenTestAnswerResourceBuilder = TestAnswerResource.builder();

        testAnswerMapper.toResourceAfterMapping(givenTestAnswer, givenTestAnswerResourceBuilder);

        var expectedTestAnswerResourceBuilder = TestAnswerResource.builder()
                .testProgressId(TEST_PROGRESS_ID)
                .optionId(OPTION_ID);

        assertEquals(expectedTestAnswerResourceBuilder.build(),
                givenTestAnswerResourceBuilder.build());
    }
    @Test
    void toResourceAfterMappingWithoutOption() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .build();
        var givenTestAnswer = TestAnswer.builder()
                .testProgress(givenTestProgress)
                .build();

        var givenTestAnswerResourceBuilder = TestAnswerResource.builder();

        testAnswerMapper.toResourceAfterMapping(givenTestAnswer, givenTestAnswerResourceBuilder);

        var expectedTestAnswerResourceBuilder = TestAnswerResource.builder()
                .testProgressId(TEST_PROGRESS_ID);

        assertEquals(expectedTestAnswerResourceBuilder.build(),
                givenTestAnswerResourceBuilder.build());
    }
}