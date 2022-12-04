package ru.agr.backend.looksliketests.service;

import ru.agr.backend.looksliketests.db.entity.main.Question;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface QuestionService {
    List<Question> findByTestId(Long testId);
}
