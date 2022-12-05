package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.controller.test.dto.TestResource;
import ru.agr.backend.looksliketests.controller.test.dto.TestsResource;
import ru.agr.backend.looksliketests.db.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
public interface TestResourceService {
    void populateTest(@NonNull Test... tests);
    TestsResource prepareTestsResource(@NonNull Page<Test> testsPage, @NonNull Pageable pageable);
    TestResource prepareTestResource(@NonNull Test test);
}
