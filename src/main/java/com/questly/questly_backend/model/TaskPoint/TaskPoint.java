package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.LatLong.LatLong;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class TaskPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long taskId;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Embedded
    private LatLong location;

    private String authorUserId;

    private float rating;

    public TaskPoint() {
        this.rating = 0;
        this.status = TaskStatus.PENDING;
    }
}

