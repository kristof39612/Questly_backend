package com.questly.questly_backend.model.TaskPoint;

import com.questly.questly_backend.model.LatLong.LatLong;
import com.questly.questly_backend.model.Task.*;
import com.questly.questly_backend.model.User.Role;
import com.questly.questly_backend.model.User.User;
import com.questly.questly_backend.model.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskPointService {

    private final TaskPointRepository taskPointRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskPointService(TaskPointRepository taskPointRepository, TaskRepository taskRepository, UserService userService) {
        this.taskPointRepository = taskPointRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public boolean checkIfTaskPointExists(Long taskPointId) {
        return taskPointRepository.existsById(taskPointId);
    }

    private void checkAdminPermission(){
        User user = userService.getLoggedInUser();
        if(user.getRole() != Role.ADMIN) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public Long getLoggedInUserId(){
        User user = userService.getLoggedInUser();
        Long userId = user.getId();
        if(userId == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        return userId;
    }

    public void updateTaskPointRating(Long taskPointId, float newRating) {
        TaskPoint taskPoint = taskPointRepository.findById(taskPointId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskPoint not found"));

        taskPoint.setRating(newRating);
        taskPointRepository.save(taskPoint);
    }

    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return mapTaskEntityToDTO(task);
    }

    public TaskPointDTO getTaskPointById(Long taskPointId) {
        TaskPoint taskPoint = taskPointRepository.findById(taskPointId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskPoint not found"));
        return mapToDTO(taskPoint);
    }

    public List<TaskPointDTO> getAllTaskPoints() {
        return taskPointRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public TaskPointDTO updateStatus(Long id, TaskStatus status) {

        checkAdminPermission();

        TaskPoint taskPoint = taskPointRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskPoint not found"));
        taskPoint.setStatus(status);
        TaskPoint updatedTaskPoint = taskPointRepository.save(taskPoint);
        return mapToDTO(updatedTaskPoint);
    }

    public void deleteTaskPoint(Long id) {

        checkAdminPermission();

        TaskPoint taskPoint = taskPointRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskPoint not found"));

        // Delete the corresponding Task
        taskRepository.deleteById(taskPoint.getTaskId());

        // Now delete the TaskPoint
        taskPointRepository.deleteById(id);
    }

    public TaskPointDTO saveTaskPoint(TaskPointDTO taskPointDTO) {
        // Map and save Task entity
        Task task = mapTaskDTOToEntity(taskPointDTO.getTask());
        task = taskRepository.save(task);

        // Map LatLong entity
        LatLong latLong = new LatLong();
        latLong.setLatitude(taskPointDTO.getLocation().getLatitude());
        latLong.setLongitude(taskPointDTO.getLocation().getLongitude());

        // Create TaskPoint with saved IDs
        TaskPoint taskPoint = new TaskPoint();
        //taskPoint.setId(taskPointDTO.getId());
        taskPoint.setTitle(taskPointDTO.getTitle());
        taskPoint.setTaskId(task.getId());
        taskPoint.setLocation(latLong);
        taskPoint.setStatus(TaskStatus.PENDING);
        taskPoint.setAuthorUserId(getLoggedInUserId().toString());
        taskPoint.setRating(taskPointDTO.getRating());

        TaskPoint savedTaskPoint = taskPointRepository.save(taskPoint);
        return mapToDTO(savedTaskPoint);
    }

    private TaskPointDTO mapToDTO(TaskPoint taskPoint) {
        TaskPointDTO dto = new TaskPointDTO();
        dto.setId(taskPoint.getId());
        dto.setTitle(taskPoint.getTitle());
        dto.setStatus(taskPoint.getStatus());
        dto.setRating(taskPoint.getRating());
        dto.setAuthorUserId(taskPoint.getAuthorUserId());
        dto.setLocation(taskPoint.getLocation());
        dto.setTask(getTaskById(taskPoint.getTaskId()));
        return dto;
    }

    private Task mapTaskDTOToEntity(TaskDTO taskDTO) {
        Task task;


        switch (taskDTO) {
            case GoToPointTaskDTO goToPointTaskDTO-> {
                task = new GoToPointTask();
                ((GoToPointTask) task).setWhere(goToPointTaskDTO.getWhere());
            }
            case SingleChoiceTaskDTO singleChoiceTaskDTO -> {
                task = new SingleChoiceTask();
                ((SingleChoiceTask) task).setQuestion(singleChoiceTaskDTO.getQuestion());
                ((SingleChoiceTask) task).setChoices(singleChoiceTaskDTO.getChoices());
                ((SingleChoiceTask) task).setCorrectAnswer(singleChoiceTaskDTO.getCorrectAnswer());
            }
            case TextPromptTaskDTO textPromptTaskDTO -> {
                task = new TextPromptTask();
                ((TextPromptTask) task).setQuestion(textPromptTaskDTO.getQuestion());
                ((TextPromptTask) task).setAnswer(textPromptTaskDTO.getAnswer());
            }
            default -> throw new IllegalArgumentException("Unknown task type");
        }

        //task.setId(taskDTO.getId());
        task.setPointsForCompletion(taskDTO.getPointsForCompletion());

        return task;
    }

    private TaskDTO mapTaskEntityToDTO(Task task) {
        switch (task) {
            case GoToPointTask goToPointTask -> {
                GoToPointTaskDTO dto = new GoToPointTaskDTO();
                dto.setId(goToPointTask.getId());
                dto.setPointsForCompletion(goToPointTask.getPointsForCompletion());
                dto.setWhere(goToPointTask.getWhere());
                //dto.setType("GoToPointTask");
                return dto;
            }
            case SingleChoiceTask singleChoiceTask -> {
                SingleChoiceTaskDTO dto = new SingleChoiceTaskDTO();
                dto.setId(singleChoiceTask.getId());
                dto.setPointsForCompletion(singleChoiceTask.getPointsForCompletion());
                dto.setQuestion(singleChoiceTask.getQuestion());
                dto.setChoices(singleChoiceTask.getChoices());
                dto.setCorrectAnswer(singleChoiceTask.getCorrectAnswer());
                //dto.setType("SingleChoiceTask");
                return dto;
            }
            case TextPromptTask textPromptTask -> {
                TextPromptTaskDTO dto = new TextPromptTaskDTO();
                dto.setId(textPromptTask.getId());
                dto.setPointsForCompletion(textPromptTask.getPointsForCompletion());
                dto.setQuestion(textPromptTask.getQuestion());
                dto.setAnswer(textPromptTask.getAnswer());
                //dto.setType("TextPromptTask");
                return dto;
            }
            default -> throw new IllegalArgumentException("Unknown task type");
        }
    }
}