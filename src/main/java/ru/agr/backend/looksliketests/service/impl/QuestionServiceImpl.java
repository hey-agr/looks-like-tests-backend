package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.repository.QuestionRepository;
import ru.agr.backend.looksliketests.service.QuestionService;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public List<Question> findByTestId(@NonNull Long testId) {
        return questionRepository.findAllByTestId(testId);
    }
}
