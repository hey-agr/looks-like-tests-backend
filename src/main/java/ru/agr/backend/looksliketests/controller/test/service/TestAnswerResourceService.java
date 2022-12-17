package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.controller.resources.TestAnswerResource;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

/**
 * @author Arslan Rabadanov
 */
public interface TestAnswerResourceService {
    TestAnswerResource toResource(@NonNull TestAnswer testAnswer);
}
