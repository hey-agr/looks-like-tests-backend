package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.Option;

import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface OptionRepository extends CrudRepository<Option, Long> {
    List<Option> findAllByQuestionIdIn(Set<Long> questionIds);
}
