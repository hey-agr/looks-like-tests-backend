package ru.agr.backend.looksliketests.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.agr.backend.looksliketests.entity.main.Test;

/**
 * @author Arslan Rabadanov
 */
@Repository
public interface TestRepository
        extends CrudRepository<Test, Long> {

}
