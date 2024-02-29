package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.QuestionType;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.db.repository.TestRepository;
import ru.agr.backend.looksliketests.db.repository.filter.TestSpecificationFilter;
import ru.agr.backend.looksliketests.db.repository.specification.TestSpecification;
import ru.agr.backend.looksliketests.service.filter.TestFilter;
import ru.agr.backend.looksliketests.service.filter.TestFilterMapper;
import ru.agr.backend.looksliketests.service.impl.TestServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestServiceTest {
    private static final Long TEST_ID = 12L;
    private static final Long STUDENT1_ID = 14L;
    private static final Long STUDENT2_ID = 15L;
    private static final Long QUESTION_ID = 17L;
    private static final Long OPTION1_ID = 18L;
    private static final Long OPTION2_ID = 19L;

    @Mock
    private TestRepository testRepository;
    @Mock
    private QuestionService questionService;
    @Mock
    private TestFilterMapper testFilterMapper;
    @Mock
    private OptionService optionService;
    @Mock
    private QuestionImageService questionImageService;

    @InjectMocks
    private TestServiceImpl service;

    @Test
    void findPageable() {
        var givenPageable = Pageable.unpaged();

        var expectedTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .build();

        var expectedPage = new PageImpl<>(List.of(expectedTest));

        when(testRepository.findAll(givenPageable))
                .thenReturn(expectedPage);

        var result = service.findPageable(givenPageable);
        assertEquals(expectedPage, result);
    }

    @Test
    void findPageableNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findPageable(null));
    }

    @Test
    void findFiltered() {
        var givenPageable = Pageable.unpaged();
        var givenFilter = TestFilter.builder()
                .studentIds(List.of(STUDENT1_ID, STUDENT2_ID))
                .build();

        var expectedSpecificationFilter = TestSpecificationFilter.builder()
                .studentIds(List.of(STUDENT1_ID, STUDENT2_ID))
                .build();

        when(testFilterMapper.toTestSpecificationFilter(givenFilter))
                .thenReturn(expectedSpecificationFilter);

        var expectedSpecification = new TestSpecification(expectedSpecificationFilter);

        var expectedTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .build();

        var expectedPage = new PageImpl<>(List.of(expectedTest));

        when(testRepository.findAll(expectedSpecification, givenPageable))
                .thenReturn(expectedPage);

        assertEquals(expectedPage, service.findFiltered(givenFilter, givenPageable));
    }

    @Test
    void findFilteredNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(null, null));
        final var filter = TestFilter.builder().build();
        assertThrows(NullPointerException.class,
                () -> service.findFiltered(filter, null));
    }

    @Test
    void findById() {
        var expectedTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .build();

        when(testRepository.findById(TEST_ID))
                .thenReturn(Optional.of(expectedTest));
        assertEquals(Optional.of(expectedTest), service.findById(TEST_ID));
    }

    @Test
    void findByIdNotFound() {
        when(testRepository.findById(TEST_ID))
                .thenReturn(Optional.empty());
        assertEquals(Optional.empty(), service.findById(TEST_ID));
    }

    @Test
    void findByIdNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findById(null));
    }

    @Test
    void save() {
        var givenTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .build();

        service.save(givenTest);

        verify(testRepository, times(1))
                .save(givenTest);
        verify(questionService, never())
                .hasWritingQuestions(anyList());
    }

    @Test
    void saveNeedVerification() {
        var givenTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .questions(Set.of(
                        Question.builder().type(QuestionType.WRITING).build()
                ))
                .needVerification(false)
                .build();

        when(questionService.hasWritingQuestions(givenTest.getQuestions().stream().toList()))
                .thenReturn(true);
        when(testRepository.save(givenTest))
                .thenReturn(givenTest);

        var result = service.save(givenTest);
        assertTrue(result.getNeedVerification());
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class,
                () -> service.save(null));
    }

    @Test
    void populateTest() {
        var givenTest = TestEntity.builder()
                .id(TEST_ID)
                .name("Some test")
                .needVerification(false)
                .build();

        var givenTestQuestion = Question.builder()
                .id(QUESTION_ID)
                .type(QuestionType.OPTIONS)
                .test(givenTest)
                .build();

        var givenFirstOption = Option.builder()
                .id(OPTION1_ID)
                .question(givenTestQuestion)
                .name("Yes")
                .rightAnswer(true)
                .build();
        var givenSecondOption = Option.builder()
                .id(OPTION2_ID)
                .question(givenTestQuestion)
                .name("No")
                .rightAnswer(false)
                .build();

        when(questionService.findByTestIds(TEST_ID))
                .thenReturn(List.of(givenTestQuestion));
        when(optionService.findByQuestionIds(Set.of(QUESTION_ID)))
                .thenReturn(List.of(givenFirstOption, givenSecondOption));

        service.populateTest(givenTest);

        assertNotNull(givenTest.getQuestions());
        assertEquals(Set.of(givenTestQuestion), givenTest.getQuestions());
        assertNotNull(givenTestQuestion.getOptions());
        assertEquals(givenTestQuestion.getOptions(), Set.of(givenFirstOption, givenSecondOption));
    }

    @Test
    void populateTestNPE() {
        assertThrows(NullPointerException.class,
                () -> service.populateTest(new TestEntity[]{null}));
    }
}