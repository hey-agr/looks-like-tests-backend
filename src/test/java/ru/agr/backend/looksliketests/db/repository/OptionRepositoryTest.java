package ru.agr.backend.looksliketests.db.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.utils.SpringPostgresIntegrationTest;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arslan Rabadanov
 */
@SpringPostgresIntegrationTest
class OptionRepositoryTest {
    private static final Long QUESTION_ID = 1L;
    private static final Long OPTION_ID1 = 1L;
    private static final Long OPTION_ID2 = 2L;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void cleanup() {
        optionRepository.deleteAll();
        questionRepository.deleteAll();
        testRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "classpath:sql/init_test1.sql")
    void findAllByQuestionIdIn() {
        var givenTest = TestEntity.builder()
                .id(1L)
                .name("Option test 1")
                .description("Test with options 1")
                .duration(3600L)
                .minCorrectAnswers(1L)
                .needVerification(false)
                .attempts(3L)
                .build();

        var givenFirstQuestion = Question.builder()
                .id(QUESTION_ID)
                .type(QuestionType.OPTIONS)
                .test(givenTest)
                .name("How are you?")
                .build();

        var expectedFirstOption = Option.builder()
                .id(OPTION_ID1)
                .name("I am fine")
                .rightAnswer(Boolean.TRUE)
                .question(givenFirstQuestion)
                .build();
        var expectedSecondOption = Option.builder()
                .id(OPTION_ID2)
                .name("Not really well")
                .rightAnswer(Boolean.FALSE)
                .question(givenFirstQuestion)
                .build();

        var expectedOptions = new ArrayList<>(2);
        expectedOptions.add(expectedFirstOption);
        expectedOptions.add(expectedSecondOption);

        var options = optionRepository.findAllByQuestionIdIn(Set.of(QUESTION_ID));
        assertNotNull(options);
        assertFalse(options.isEmpty());
        assertEquals(expectedOptions, options);
    }
}