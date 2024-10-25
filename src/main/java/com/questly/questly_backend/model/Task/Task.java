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
    private String id;
    private int pointsForCompletion;

    // Constructor
    public Task(String id, int pointsForCompletion) {
        this.id = id;
        this.pointsForCompletion = pointsForCompletion;
    }
}
