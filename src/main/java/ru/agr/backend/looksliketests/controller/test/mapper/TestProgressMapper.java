package ru.agr.backend.looksliketests.controller.test.mapper;

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.TestEntity;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface TestProgressMapper {
    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "testResult", ignore = true)
    TestProgressResource toResource(TestProgress testProgress);

    default TestProgress toEntity(@NonNull TestEntity testEntity, @NonNull User user) {
        return TestProgress.builder()
                .test(testEntity)
                .user(user)
                .dateStarted(ZonedDateTime.now())
                .build();
    }
}
