package ru.agr.backend.looksliketests.service;

import lombok.NonNull;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;

import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
public interface QuestionImageService {
    List<QuestionImage> findByQuestionIds(@NonNull Set<Long> questionIds);

}
