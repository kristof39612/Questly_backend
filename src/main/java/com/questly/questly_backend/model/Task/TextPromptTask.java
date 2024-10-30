package com.questly.questly_backend.model.Task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class TextPromptTask extends Task {

    private String question;
    private String answer;

    public TextPromptTask(String id, int pointsForCompletion, String question, String answer) {
        super(pointsForCompletion);
        this.question = question;
        this.answer = answer;
        this.setType("TextPromptTask");
    }
}

