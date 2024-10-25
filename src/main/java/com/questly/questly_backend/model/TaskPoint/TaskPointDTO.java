package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.LatLong.LatLong;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskPointDTO {
    private Long id;
    private String taskId;
    private TaskStatus status;
    private LatLong location;
    private String authorID;
    private float rating;
}

