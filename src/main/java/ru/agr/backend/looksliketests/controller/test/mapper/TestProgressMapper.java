package ru.agr.backend.looksliketests.controller.test.mapper;

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface TestProgressMapper {
    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "userId", source = "user.id")
    TestProgressResource toResource(TestProgress testProgress);

    default TestProgress toEntity(@NonNull Test test, @NonNull User user) {
        return TestProgress.builder()
                .test(test)
                .user(user)
                .dateStarted(ZonedDateTime.now())
                .build();
    }
}
