package org.vasileva.simbirsofttask.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vasileva.simbirsofttask.entity.ParsedLog;

@Repository
public interface ParseLogRepository extends CrudRepository<ParsedLog, Long> {
}
