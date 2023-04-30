package ru.agr.backend.looksliketests.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.repository.OptionRepository;
import ru.agr.backend.looksliketests.service.OptionService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Service
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public List<Option> findByQuestionIds(@NonNull Set<Long> questionIds) {
        if (questionIds.isEmpty()) {
            return Collections.emptyList();
        }
        return optionRepository.findAllByQuestionIdIn(questionIds);
    }
}
