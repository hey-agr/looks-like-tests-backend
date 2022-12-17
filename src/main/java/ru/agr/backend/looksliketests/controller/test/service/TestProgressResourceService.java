package ru.agr.backend.looksliketests.controller.test.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.Test;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;

/**
 * @author Arslan Rabadanov
 */
public interface TestProgressResourceService {
    TestProgress prepareTestProgress(@NonNull Test test, @NonNull User user);
    TestProgressResource prepareTestProgressResource(@NonNull TestProgress testProgress);

}
