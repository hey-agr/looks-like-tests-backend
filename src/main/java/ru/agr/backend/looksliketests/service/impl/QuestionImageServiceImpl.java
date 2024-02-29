package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;
import ru.agr.backend.looksliketests.db.repository.QuestionImageRepository;
import ru.agr.backend.looksliketests.service.QuestionImageService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class QuestionImageServiceImpl implements QuestionImageService {
    private final QuestionImageRepository questionImageRepository;
    @Override
    public List<QuestionImage> findByQuestionIds(@NonNull Set<Long> questionIds) {
        if (questionIds.isEmpty()) {
            return Collections.emptyList();
        }
        return questionImageRepository.findAllByQuestionIdIn(questionIds);
    }
}
