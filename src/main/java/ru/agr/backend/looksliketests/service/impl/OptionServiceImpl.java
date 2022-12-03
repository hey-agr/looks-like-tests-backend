package ru.agr.backend.looksliketests.service.impl;

import org.springframework.stereotype.Service;
import ru.agr.backend.looksliketests.repository.OptionRepository;
import ru.agr.backend.looksliketests.service.OptionService;

/**
 * @author Arslan Rabadanov
 */
@Service
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
}
