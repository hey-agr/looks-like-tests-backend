package ru.agr.backend.looksliketests.controller.test.mapper;

/**
 * @author Arslan Rabadanov
 */

import org.mapstruct.Mapper;
import ru.agr.backend.looksliketests.controller.auth.mapper.UserMapper;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TestMapper.class})
public abstract class TestProgressMapper {
    public abstract TestProgressResource toResource(TestProgress testProgress);
}
