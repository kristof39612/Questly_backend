package com.questly.questly_backend.model.LogEntry;

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

    @Autowired
    public LogEntryService(LogEntryRepository logEntryRepository, PhotoRepository photoRepository) {

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

    public LogEntryDTO saveLogEntry(LogEntry logEntry) {

        return mapToDTO(logEntryRepository.save(logEntry));
    }

    public List<LogEntryDTO> getAllLogEntries() {
        return logEntryRepository.findAll().stream()
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