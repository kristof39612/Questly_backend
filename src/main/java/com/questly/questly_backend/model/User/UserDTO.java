package com.questly.questly_backend.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

//@ta1120: is this class needed?

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
}
