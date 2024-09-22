package ru.agr.backend.looksliketests.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.db.entity.main.QuestionImage;

import java.util.List;
import java.util.Set;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface QuestionImageRepository extends CrudRepository<QuestionImage, Long> {
    List<QuestionImage> findAllByQuestionIdIn(Set<Long> questionIds);

}
