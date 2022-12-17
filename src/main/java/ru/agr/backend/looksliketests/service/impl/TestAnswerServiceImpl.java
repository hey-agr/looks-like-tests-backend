package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.db.repository.TestAnswerRepository;
import ru.agr.backend.looksliketests.service.TestAnswerService;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Service
public class TestAnswerServiceImpl implements TestAnswerService {
    private final TestAnswerRepository testAnswerRepository;

    @Override
    public TestAnswer save(@NonNull TestAnswer testAnswer) {
        return testAnswerRepository.save(testAnswer);
    }
}
