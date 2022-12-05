package ru.agr.backend.looksliketests.controller.test.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.dto.QuestionResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.dto.TestResource;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.Test;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:41:39+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class TestMapperImpl extends TestMapper {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public TestResource toResource(Test test) {
        if ( test == null ) {
            return null;
        }

        TestResource.TestResourceBuilder testResource = TestResource.builder();

        testResource.minRightAnswers( test.getMinCorrectAnswers() );
        testResource.isNeedVerify( test.getNeedVerification() );
        testResource.id( test.getId() );
        testResource.name( test.getName() );
        testResource.description( test.getDescription() );
        testResource.duration( test.getDuration() );
        testResource.attempts( test.getAttempts() );
        testResource.questions( questionListToQuestionResourceList( test.getQuestions() ) );

        return testResource.build();
    }

    @Override
    public Test toEntity(CreateTestDto createTestDto) {
        if ( createTestDto == null ) {
            return null;
        }

        Test test = new Test();

        test.setMinCorrectAnswers( createTestDto.getMinRightAnswers() );
        test.setNeedVerification( createTestDto.getIsNeedVerify() );
        test.setName( createTestDto.getName() );
        test.setDescription( createTestDto.getDescription() );
        test.setDuration( createTestDto.getDuration() );
        test.setAttempts( createTestDto.getAttempts() );
        test.setQuestions( createQuestionDtoListToQuestionList( createTestDto.getQuestions() ) );

        toEntityAfterMapping( test );

        return test;
    }

    protected List<QuestionResource> questionListToQuestionResourceList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionResource> list1 = new ArrayList<QuestionResource>( list.size() );
        for ( Question question : list ) {
            list1.add( questionMapper.toQuestionResource( question ) );
        }

        return list1;
    }

    protected List<Question> createQuestionDtoListToQuestionList(List<CreateQuestionDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Question> list1 = new ArrayList<Question>( list.size() );
        for ( CreateQuestionDto createQuestionDto : list ) {
            list1.add( questionMapper.toEntity( createQuestionDto ) );
        }

        return list1;
    }
}
