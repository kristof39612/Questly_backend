package com.questly.questly_backend.model.TaskPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskpoint")
public class TaskPointController {

    @Autowired
    private TaskPointService taskPointService;

    @PostMapping
    public ResponseEntity<TaskPointDTO> createTaskPoint(@RequestBody TaskPointDTO taskPointDTO) {
        TaskPointDTO savedTaskPoint = taskPointService.saveTaskPoint(taskPointDTO);
        return ResponseEntity.ok(savedTaskPoint);
    }

    @GetMapping("/{id}")
    public TaskPointDTO getTaskPoint(@PathVariable Long id) {
        return taskPointService.getTaskPointById(id);
    }

    @GetMapping
    public ResponseEntity<List<TaskPointDTO>> getAllTaskPoints() {
        List<TaskPointDTO> taskPoints = taskPointService.getAllTaskPoints();
        return ResponseEntity.ok(taskPoints);
    }
}

