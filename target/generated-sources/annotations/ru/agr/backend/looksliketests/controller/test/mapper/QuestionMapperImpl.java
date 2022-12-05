package ru.agr.backend.looksliketests.controller.test.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.dto.OptionResource;
import ru.agr.backend.looksliketests.controller.dto.QuestionResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateOptionDto;
import ru.agr.backend.looksliketests.controller.test.dto.CreateQuestionDto;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.Question;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:23:47+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class QuestionMapperImpl extends QuestionMapper {

    @Autowired
    private OptionMapper optionMapper;

    @Override
    public QuestionResource toQuestionResource(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResource.QuestionResourceBuilder questionResource = QuestionResource.builder();

        questionResource.answers( optionListToOptionResourceList( question.getOptions() ) );
        questionResource.id( question.getId() );
        questionResource.name( question.getName() );
        if ( question.getType() != null ) {
            questionResource.type( question.getType().name() );
        }

        return questionResource.build();
    }

    @Override
    public Question toEntity(CreateQuestionDto createQuestionDto) {
        if ( createQuestionDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setOptions( createOptionDtoListToOptionList( createQuestionDto.getAnswers() ) );
        question.setName( createQuestionDto.getName() );
        question.setType( createQuestionDto.getType() );

        return question;
    }

    protected List<OptionResource> optionListToOptionResourceList(List<Option> list) {
        if ( list == null ) {
            return null;
        }

        List<OptionResource> list1 = new ArrayList<OptionResource>( list.size() );
        for ( Option option : list ) {
            list1.add( optionMapper.toOptionResource( option ) );
        }

        return list1;
    }

    protected Option createOptionDtoToOption(CreateOptionDto createOptionDto) {
        if ( createOptionDto == null ) {
            return null;
        }

        Option option = new Option();

        option.setName( createOptionDto.getName() );

        return option;
    }

    protected List<Option> createOptionDtoListToOptionList(List<CreateOptionDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Option> list1 = new ArrayList<Option>( list.size() );
        for ( CreateOptionDto createOptionDto : list ) {
            list1.add( createOptionDtoToOption( createOptionDto ) );
        }

        return list1;
    }
}
