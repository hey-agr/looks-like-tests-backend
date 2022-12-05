package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.Question;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAllByTestIdIn(Long... testIds);
}
