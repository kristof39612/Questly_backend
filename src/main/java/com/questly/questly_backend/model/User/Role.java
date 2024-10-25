package com.questly.questly_backend.model.User;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    USER,
    ADMIN
}
