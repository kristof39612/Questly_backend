package com.questly.questly_backend.model.Task;

import com.questly.questly_backend.model.LatLong.LatLong;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class GoToPointTask extends Task {

    @Embedded
    private LatLong where;

    public GoToPointTask(String id, int pointsForCompletion, LatLong where) {
        super(pointsForCompletion);
        this.setType("GoToPointTask");
        this.where = where;
    }
}
