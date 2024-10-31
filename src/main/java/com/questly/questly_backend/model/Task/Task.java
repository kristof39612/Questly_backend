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
    private Long id;

    private int pointsForCompletion;

    public Task(int pointsForCompletion) {
        this.pointsForCompletion = pointsForCompletion;
    }
}
