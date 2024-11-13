package com.questly.questly_backend.model.Leaderboard;

import com.questly.questly_backend.model.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final UserService userService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService, UserService userService) {
        this.leaderboardService = leaderboardService;
        this.userService = userService;
    }

    @GetMapping("/user/points")
    public ResponseEntity<UserPointsDTO> getUserPoints() {
        UserPointsDTO userPoints = leaderboardService.getUserTotalPoints(userService.getLoggedInUserId());
        return ResponseEntity.ok().body(userPoints);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserPointsDTO>> getLeaderboard() {
        List<UserPointsDTO> leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok().body(leaderboard);
    }
}
