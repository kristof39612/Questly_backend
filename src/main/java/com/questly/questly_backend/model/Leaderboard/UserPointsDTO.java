package com.questly.questly_backend.model.Leaderboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPointsDTO {
    private String username;
    private int points;
}