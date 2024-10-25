package com.questly.questly_backend.model.Task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class SingleChoiceTask extends Task {

    private String question;

    private String[] answers;

    private int correctAnswer;

    public SingleChoiceTask(String id, int pointsForCompletion, String question, String[] answers, int correctAnswer) {
        super(id, pointsForCompletion);
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}

