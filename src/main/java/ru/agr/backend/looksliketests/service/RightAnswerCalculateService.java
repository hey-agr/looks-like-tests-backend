package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface RightAnswerCalculateService {
    boolean isRightAnswer(@NonNull Question question, @NonNull List<TestAnswer> answers);
}
