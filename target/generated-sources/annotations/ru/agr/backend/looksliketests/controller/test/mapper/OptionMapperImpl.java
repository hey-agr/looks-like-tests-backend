package ru.agr.backend.looksliketests.controller.test.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.agr.backend.looksliketests.controller.dto.OptionResource;
import ru.agr.backend.looksliketests.db.entity.main.Option;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-05T17:41:39+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4 (Azul Systems, Inc.)"
)
@Component
public class OptionMapperImpl extends OptionMapper {

    @Override
    public OptionResource toOptionResource(Option option) {
        if ( option == null ) {
            return null;
        }

        OptionResource.OptionResourceBuilder optionResource = OptionResource.builder();

        optionResource.id( option.getId() );
        optionResource.name( option.getName() );

        return optionResource.build();
    }
}
