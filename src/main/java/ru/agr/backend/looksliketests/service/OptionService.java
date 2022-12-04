package ru.agr.backend.looksliketests.service;

import ru.agr.backend.looksliketests.db.entity.main.Option;

import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
public interface OptionService {
    List<Option> findByQuestionIds(Set<Long> questionIds);
}
