package com.questly.questly_backend.model.User;

import com.questly.questly_backend.model.LogEntry.*;
import com.questly.questly_backend.model.TaskPoint.TaskPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final LogEntryService logEntryService;
    private final TaskPointRepository taskPointRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, LogEntryService logEntryService, TaskPointRepository taskPointRepository) {
        this.userService = userService;
        this.logEntryService = logEntryService;
        this.taskPointRepository = taskPointRepository;
    }

    @PostMapping("/completeTask")
    public ResponseEntity<LogEntryDTO> completeTask(@ModelAttribute CompleteTaskDTO completeTaskDTO) {
        try {

            Photo photo = logEntryService.savePhoto(completeTaskDTO);

            LogEntry logEntry = new LogEntry();

            logEntry.setVisitDate(LocalDateTime.now());

            User user = userService.getLoggedInUser();
            if( user.getCurrentTaskPointId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no active task");
            logEntry.setVisitedPointId(user.getCurrentTaskPointId());
            logEntry.setUserId(user.getId());

            logEntry.setGivenRating(completeTaskDTO.getGivenRating());
            logEntry.setPhotoId(photo.getId());

            LogEntryDTO savedLogEntry = logEntryService.saveLogEntry(logEntry);

            userService.setTask(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedLogEntry);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        return logEntryService.getPhoto(id);
    }

    @PatchMapping("/startTask")
    public ResponseEntity<Void> startTask(@RequestBody StartTaskDTO startTaskDTO) {
        Long taskPointId = startTaskDTO.getTaskPointId();
        if(!taskPointRepository.existsById(taskPointId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskPoint does not exist: " + taskPointId);
        logEntryService.checkTaskPointCompletedByUser(userService.getLoggedInUserId(), taskPointId);
        userService.setTask(taskPointId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancelTask")
    public ResponseEntity<Void> cancelTask() {
        userService.setTask(null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/currentTask")
    public ResponseEntity<StartTaskDTO> getCurrentTask() {
        User user = userService.getLoggedInUser();
        StartTaskDTO taskDTO = new StartTaskDTO(user.getCurrentTaskPointId());
        return taskDTO.getTaskPointId() != null ? ResponseEntity.ok().body(taskDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getLogEntries")
    public ResponseEntity<List<LogEntryDTO>> getUserLogEntries() {
        User user = userService.getLoggedInUser();
        List<LogEntryDTO> entries = logEntryService.getUserLogEntries(user.getId());
        return !entries.isEmpty() ? ResponseEntity.ok().body(entries) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole() {
        String role = userService.getLoggedInUser().getRole().toString();
        int roleInt = 0;
        if(role.equals("ADMIN")) roleInt = 1;
        return ResponseEntity.ok(Integer.toString(roleInt));
    }

    @GetMapping("/userID")
    public ResponseEntity<String> getUserID() {
        return ResponseEntity.ok(userService.getLoggedInUserId().toString());
    }
}

