package com.questly.questly_backend.model.User;

import com.questly.questly_backend.model.LogEntry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LogEntryService logEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("/completeTask")
    public ResponseEntity<LogEntryDTO> completeTask(@ModelAttribute CompleteTaskDTO completeTaskDTO) {
        try {

            Photo photo = logEntryService.savePhoto(completeTaskDTO);

            LogEntry logEntry = new LogEntry();

            logEntry.setVisitDate(LocalDateTime.now());

            User user = userService.getLoggedInUser();
            //if( user.getCurrentTaskPointId() == null) throw new RuntimeException();
            /*user.getCurrentTaskPointId()*/
            logEntry.setVisitedPointId(user.getId());
            logEntry.setUserId(user.getId());

            logEntry.setGivenRating(completeTaskDTO.getGivenRating());
            logEntry.setPhotoId(photo.getId());

            LogEntryDTO savedLogEntry = logEntryService.saveLogEntry(logEntry);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedLogEntry);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        return logEntryService.getPhoto(id);
    }
}

