package org.vasileva.simbirsofttask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vasileva.simbirsofttask.entity.LogLevel;

@Repository
public interface LogLevelRepository extends CrudRepository<LogLevel, Long>, JpaRepository<LogLevel, Long> {


    LogLevel findByDescription(@Param("description") String name);
}
