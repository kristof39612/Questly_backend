package com.questly.questly_backend.model.TaskPoint;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaskStatus {
    PENDING,
    APPROVED,
    REJECTED
}
