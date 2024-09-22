package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.Question;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface QuestionService {
    List<Question> findByTestIds(@NonNull Long... testIds);
    boolean hasWritingQuestions(@NonNull List<Question> questions);
}
