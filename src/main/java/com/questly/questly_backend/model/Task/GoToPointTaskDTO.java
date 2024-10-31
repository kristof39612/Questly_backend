package com.questly.questly_backend.model.Task;

import com.questly.questly_backend.model.LatLong.LatLong;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoToPointTaskDTO extends TaskDTO {
    private LatLong where;
}
