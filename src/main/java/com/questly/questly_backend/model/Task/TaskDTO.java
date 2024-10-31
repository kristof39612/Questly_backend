package com.questly.questly_backend.model.Task;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextPromptTaskDTO.class, name = "TextPromptTask"),
        @JsonSubTypes.Type(value = SingleChoiceTaskDTO.class, name = "SingleChoiceTask"),
        @JsonSubTypes.Type(value = GoToPointTaskDTO.class, name = "GoToPointTask")
})
@Data
@NoArgsConstructor
public abstract class TaskDTO {
    private Long id;
    private int pointsForCompletion;
}
