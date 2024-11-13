package com.questly.questly_backend.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class CompleteTaskDTO {
    private MultipartFile photo;
    private Integer givenRating;
}
