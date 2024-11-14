package com.questly.questly_backend.model.Leaderboard;

import com.questly.questly_backend.model.LogEntry.LogEntryDTO;
import com.questly.questly_backend.model.LogEntry.LogEntryService;
import com.questly.questly_backend.model.TaskPoint.TaskPointDTO;
import com.questly.questly_backend.model.TaskPoint.TaskPointService;
import com.questly.questly_backend.model.User.User;
import com.questly.questly_backend.model.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final LogEntryService logEntryService;
    private final UserService userService;
    private final TaskPointService taskPointService;

    @Autowired
    public LeaderboardService(LogEntryService logEntryService, UserService userService, TaskPointService taskPointService) {
        this.logEntryService = logEntryService;
        this.userService = userService;
        this.taskPointService = taskPointService;
    }

    public UserPointsDTO getUserTotalPoints(Long userId) {
        List<LogEntryDTO> userEntries = logEntryService.getUserLogEntries(userId);
        int points = 0;
        for (LogEntryDTO userEntry : userEntries) {
            Long taskPointId = userEntry.getVisitedPointId();
            if(taskPointService.checkIfTaskPointExists(taskPointId)) {
                points += taskPointService.getTaskPointById(taskPointId).getTask().getPointsForCompletion();
            }
        }
        UserPointsDTO userPointsDTO = new UserPointsDTO();
        userPointsDTO.setPoints(points);
        userPointsDTO.setUsername(userService.getLoggedInUser().getUsername());
        return userPointsDTO;
    }

    public List<UserPointsDTO> getLeaderboard() {
        List<User> users = userService.getAllUsers();
        Map<String, Integer> userPointsMap = new HashMap<>();

        for (User user : users) {
            UserPointsDTO userPoints = getUserTotalPoints(user.getId());
            int totalPoints = userPoints.getPoints();
            userPointsMap.put(user.getUsername(), totalPoints);
        }

        return userPointsMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> new UserPointsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

