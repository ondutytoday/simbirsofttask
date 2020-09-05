package org.vasileva.simbirsofttask.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.vasileva.simbirsofttask.entity.LogLevel;

public interface LogLevelRepository extends CrudRepository<LogLevel, Long> {
}
