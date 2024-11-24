package com.questly.questly_backend.model.LogEntry;

import com.questly.questly_backend.model.TaskPoint.TaskPointService;
import com.questly.questly_backend.model.User.CompleteTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogEntryService {
    private final LogEntryRepository logEntryRepository;
    private final PhotoRepository photoRepository;
    private final TaskPointService taskPointService;

    @Autowired
    public LogEntryService(LogEntryRepository logEntryRepository, PhotoRepository photoRepository, TaskPointService taskPointService) {
        this.taskPointService = taskPointService;
        this.logEntryRepository = logEntryRepository;
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(CompleteTaskDTO completeTaskDTO) throws IOException {
        Photo photo = new Photo();
        photo.setData(completeTaskDTO.getPhoto().getBytes());
        photo = photoRepository.save(photo);
        return photo;
    }

    public ResponseEntity<byte[]> getPhoto(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }

    public void checkTaskPointCompletedByUser(Long userId, Long taskPointId) {
        List<LogEntry> logEntries = logEntryRepository.findByUserId(userId);
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getVisitedPointId().equals(taskPointId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already completed taskpoint: " + taskPointId);
        }
    }

    public void updateAverageRatingForTaskPoint(Long taskPointId) {
        List<LogEntry> logEntries = logEntryRepository.findByVisitedPointId(taskPointId);

        if (logEntries.isEmpty()) return;

        double averageRating = logEntries.stream()
                .mapToInt(LogEntry::getGivenRating)
                .average()
                .orElse(0);

        taskPointService.updateTaskPointRating(taskPointId, (float) averageRating);
    }

    public LogEntryDTO saveLogEntry(LogEntry logEntry) {

        return mapToDTO(logEntryRepository.save(logEntry));
    }

    public List<LogEntryDTO> getUserLogEntries(Long userId) {
        List<LogEntry> logEntries = logEntryRepository.findByUserId(userId);
        for (LogEntry logEntry : logEntries) {
            if (!taskPointService.checkIfTaskPointExists(logEntry.getVisitedPointId())){
                logEntryRepository.delete(logEntry);
            }
        }
        return logEntries.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LogEntryDTO mapToDTO(LogEntry logEntry) {
        LogEntryDTO dto = new LogEntryDTO();
        dto.setId(logEntry.getId());
        dto.setVisitedPointId(logEntry.getVisitedPointId());
        dto.setVisitDate(logEntry.getVisitDate());
        dto.setUserId(logEntry.getUserId());
        dto.setGivenRating(logEntry.getGivenRating());
        dto.setPhotoId(logEntry.getPhotoId());
        return dto;
    }
}