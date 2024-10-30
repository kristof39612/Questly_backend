package com.questly.questly_backend.model.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SingleChoiceTaskDTO extends TaskDTO {
    private String question;
    private String[] choices;
    private int correctAnswer;
}
