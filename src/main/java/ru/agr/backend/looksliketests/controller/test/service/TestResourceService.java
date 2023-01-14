package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.StudentTestHistoryCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.StudentTestHistory;
import ru.agr.backend.looksliketests.db.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
public interface TestResourceService {
    TestCollectionResource prepareTestsResource(@NonNull Page<Test> testsPage, @NonNull Pageable pageable);
    StudentTestAssignationCollectionResource prepareStudentTestAssignationsResource(@NonNull User user, @NonNull Page<Test> testsPage, @NonNull Pageable pageable);
    StudentTestHistoryCollectionResource prepareStudentTestHistoryCollectionResource(@NonNull Page<StudentTestHistory> testsPage, @NonNull Pageable pageable);
    TestResource prepareTestResource(@NonNull Test test);
}
