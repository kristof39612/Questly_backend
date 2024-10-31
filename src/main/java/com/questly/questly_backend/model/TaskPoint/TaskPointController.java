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
    public ResponseEntity<TaskPointDTO> getTaskPoint(@PathVariable Long id) {
        TaskPointDTO taskPointDTO =  taskPointService.getTaskPointById(id);
        return ResponseEntity.ok(taskPointDTO);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<TaskPointDTO> approveTaskPoint(@PathVariable Long id) {
        TaskPointDTO updatedTaskPoint = taskPointService.updateStatus(id, TaskStatus.APPROVED);
        return ResponseEntity.ok(updatedTaskPoint);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<TaskPointDTO> rejectTaskPoint(@PathVariable Long id) {
        TaskPointDTO updatedTaskPoint = taskPointService.updateStatus(id, TaskStatus.REJECTED);
        return ResponseEntity.ok(updatedTaskPoint);
    }

    @GetMapping
    public ResponseEntity<List<TaskPointDTO>> getAllTaskPoints() {
        List<TaskPointDTO> taskPoints = taskPointService.getAllTaskPoints();
        return ResponseEntity.ok(taskPoints);
    }
}

