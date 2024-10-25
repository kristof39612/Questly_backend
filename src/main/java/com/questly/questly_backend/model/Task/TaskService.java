package com.questly.questly_backend.model.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {this.taskRepository = taskRepository;}

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = mapToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }

    public TaskDTO getTaskById(String id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TaskPoint not found"));
        return mapToDTO(task);
    }

    private TaskDTO mapToDTO(Task task) {
        if (task instanceof GoToPointTask) {
            GoToPointTaskDTO dto = new GoToPointTaskDTO();
            dto.setWhere(((GoToPointTask) task).getWhere());
            return dto;
        }
        if (task instanceof SingleChoiceTask) {
            SingleChoiceTaskDTO dto = new SingleChoiceTaskDTO();
            dto.setQuestion(((SingleChoiceTask) task).getQuestion());
            dto.setAnswers(((SingleChoiceTask) task).getAnswers());
            dto.setCorrectAnswer(((SingleChoiceTask) task).getCorrectAnswer());
            return dto;
        }
        if (task instanceof TextPromptTask) {
            TextPromptTaskDTO dto = new TextPromptTaskDTO();
            dto.setQuestion(((TextPromptTask) task).getQuestion());
            dto.setAnswer(((TextPromptTask) task).getAnswer());
            return dto;
        }
        else return null;
    }

    private Task mapToEntity(TaskDTO dto) {
        if (dto instanceof GoToPointTaskDTO) {
            GoToPointTask entity = new GoToPointTask();
            entity.setWhere(((GoToPointTaskDTO) dto).getWhere());
            entity.setPointsForCompletion(dto.getPointsForCompletion());
            return entity;
        }
        if (dto instanceof SingleChoiceTaskDTO) {
            SingleChoiceTask entity = new SingleChoiceTask();
            entity.setQuestion(((SingleChoiceTaskDTO) dto).getQuestion());
            entity.setAnswers(((SingleChoiceTaskDTO) dto).getAnswers());
            entity.setCorrectAnswer(((SingleChoiceTaskDTO) dto).getCorrectAnswer());
            entity.setPointsForCompletion(dto.getPointsForCompletion());
            return entity;
        }
        if (dto instanceof TextPromptTaskDTO) {
            TextPromptTask entity = new TextPromptTask();
            entity.setQuestion(((TextPromptTaskDTO) dto).getQuestion());
            entity.setAnswer(((TextPromptTaskDTO) dto).getAnswer());
            entity.setPointsForCompletion(dto.getPointsForCompletion());
            return entity;
        }
        else return null;
    }

}

