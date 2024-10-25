package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.Task.Task;
import com.questly.questly_backend.model.Task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskPointService {

    private final TaskPointRepository taskPointRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskPointService(TaskPointRepository taskPointRepository, TaskRepository taskRepository) {
        this.taskPointRepository = taskPointRepository;
        this.taskRepository = taskRepository;
    }

    public TaskPointDTO saveTaskPoint(TaskPointDTO taskPointDTO) {
        TaskPoint taskPoint = mapToEntity(taskPointDTO);
        TaskPoint savedTaskPoint = taskPointRepository.save(taskPoint);
        return mapToDTO(savedTaskPoint);
    }

    private TaskPoint mapToEntity(TaskPointDTO dto) {
        TaskPoint taskPoint = new TaskPoint();
        taskPoint.setAuthorID(dto.getAuthorID());
        taskPoint.setLocation(dto.getLocation());

        Task task = fetchTaskById(dto.getTaskId());
        taskPoint.setTask(task);
        return taskPoint;
    }

    private TaskPointDTO mapToDTO(TaskPoint taskPoint) {
        TaskPointDTO dto = new TaskPointDTO();
        dto.setId(taskPoint.getId());
        dto.setStatus(taskPoint.getStatus());
        dto.setRating(taskPoint.getRating());
        dto.setAuthorID(taskPoint.getAuthorID());
        dto.setLocation(taskPoint.getLocation());

        if (taskPoint.getTask() != null) {
            dto.setTaskId(taskPoint.getTask().getId());
        }
        return dto;
    }

    private Task fetchTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public TaskPointDTO getTaskPointById(String taskPointId) {
        TaskPoint taskPoint = taskPointRepository.findById(taskPointId)
                .orElseThrow(() -> new RuntimeException("TaskPoint not found"));
        return mapToDTO(taskPoint);
    }
}

