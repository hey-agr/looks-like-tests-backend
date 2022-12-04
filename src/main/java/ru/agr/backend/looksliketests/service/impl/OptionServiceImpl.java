package ru.agr.backend.looksliketests.service.impl;

import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.db.entity.main.Option;
import ru.agr.backend.looksliketests.db.repository.OptionRepository;
import ru.agr.backend.looksliketests.service.OptionService;

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
    public List<Option> findByQuestionIds(Set<Long> questionIds) {
        return optionRepository.findAllByQuestionIdIn(questionIds);
    }
}
