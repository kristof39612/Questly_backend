package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.LatLong.LatLong;
import com.questly.questly_backend.model.Task.Task;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class TaskPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Embedded
    private LatLong location;

    private String authorID;

    private float rating;

    public TaskPoint() {
        this.rating = 0;
        this.status = TaskStatus.PENDING;
    }
}

