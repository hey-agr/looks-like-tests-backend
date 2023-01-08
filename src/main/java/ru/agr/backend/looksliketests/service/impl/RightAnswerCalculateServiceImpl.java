package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.entity.main.Question;
import ru.agr.backend.looksliketests.db.entity.main.TestAnswer;
import ru.agr.backend.looksliketests.service.RightAnswerCalculateService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arslan Rabadanov
 */
@Service
public class RightAnswerCalculateServiceImpl implements RightAnswerCalculateService {
    @Override
    public boolean isRightAnswer(@NonNull Question question, @NonNull List<TestAnswer> answers) {
        if (answers.isEmpty()) {
            return false;
        }
        switch (question.getType()) {
            case OPTIONS -> {
                final var answer = answers.get(0);
                return question.getOptions().stream()
                        .filter(Option::getRightAnswer)
                        .anyMatch(option -> option.getId().equals(answer.getOption().getId()));
            }
            case OPTIONS_MULTIPLY -> {
                final var rightAnswers = question.getOptions().stream()
                        .filter(Option::getRightAnswer)
                        .map(Option::getId)
                        .collect(Collectors.toSet());
                final var chosenAnswers = answers.stream()
                        .map(TestAnswer::getOption)
                        .map(Option::getId)
                        .collect(Collectors.toSet());
                return rightAnswers.equals(chosenAnswers);
            }
            default -> {
                return false;
            }
        }
    }
}
