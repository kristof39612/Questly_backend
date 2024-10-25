package com.questly.questly_backend.model.TaskPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskpoints")
public class TaskPointController {

    @Autowired
    private TaskPointService taskPointService;

    @PostMapping
    public TaskPointDTO createTaskPoint(@RequestBody TaskPointDTO taskPointDTO) {
        return taskPointService.saveTaskPoint(taskPointDTO);
    }

    @GetMapping("/{id}")
    public TaskPointDTO getTaskPoint(@PathVariable String id) {
        return taskPointService.getTaskPointById(id);
    }
}

