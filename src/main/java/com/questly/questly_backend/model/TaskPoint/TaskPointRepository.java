package com.questly.questly_backend.model.TaskPoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPointRepository extends JpaRepository<TaskPoint, String> {
}