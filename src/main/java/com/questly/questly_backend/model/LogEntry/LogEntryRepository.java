package com.questly.questly_backend.model.LogEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByUserId(Long userId);
    List<LogEntry> findByVisitedPointId(Long visitedPointId);
}
