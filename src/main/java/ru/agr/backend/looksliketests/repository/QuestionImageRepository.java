package ru.agr.backend.looksliketests.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.entity.main.QuestionImage;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface QuestionImageRepository
        extends CrudRepository<QuestionImage, Long> {

}