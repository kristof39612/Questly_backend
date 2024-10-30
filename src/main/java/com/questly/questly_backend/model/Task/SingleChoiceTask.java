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

    private String[] choices;

    private int correctAnswer;

    public SingleChoiceTask(String id, int pointsForCompletion, String question, String[] choices, int correctAnswer) {
        super(pointsForCompletion);
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.setType("SingleChoiceTask");
    }
}

