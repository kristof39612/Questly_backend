package com.questly.questly_backend.model.Task;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private String id;

    private int pointsForCompletion;

    public Task(String id, int pointsForCompletion) {
        this.id = id;
        this.pointsForCompletion = pointsForCompletion;
    }
}
