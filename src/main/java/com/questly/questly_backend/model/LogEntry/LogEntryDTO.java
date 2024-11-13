package com.questly.questly_backend.model.LogEntry;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogEntryDTO {
    private Long id;
    private Long visitedPointId;
    private LocalDateTime visitDate;
    private Long userId;
    private Long photoId;
    private Integer givenRating;
}
