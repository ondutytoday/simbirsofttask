package org.vasileva.simbirsofttask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vasileva.simbirsofttask.entity.SourceThread;

@Repository
public interface SourceThreadRepository extends CrudRepository<SourceThread, Long>, JpaRepository<SourceThread, Long> {

    @Query("SELECT t FROM SourceThread t WHERE LOWER(t.threadName) = LOWER(:name)")
    SourceThread findByName(@Param("thread_name") String name);
}
