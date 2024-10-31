package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.LatLong.LatLong;
import com.questly.questly_backend.model.Task.TaskDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskPointDTO {
    private Long id;
    private String title;
    private TaskDTO task;
    private TaskStatus status;
    private LatLong location;
    private String authorUserId;
    private float rating;
}

