package com.goldbeardsea.taskmaster.controller;

import com.goldbeardsea.taskmaster.model.Task;
import com.goldbeardsea.taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    //tasks
    @GetMapping("/tasks")
    public ArrayList<Task> getAllTasks(Model m) {
        ArrayList<Task> arrayList = (ArrayList<Task>) taskRepository.findAll();
        return arrayList;
    }

    @PostMapping("/tasks")
    public Task taskCreation(@RequestParam String title, @RequestParam String description) {
        Task newTask = new Task(title, description, "Available");
        taskRepository.save(newTask);
        return newTask;
    }

    @GetMapping("/users/{name}/tasks")
    public ArrayList<Task> getTasks(@RequestParam String name) {
        ArrayList arrayList = (ArrayList) taskRepository.findAll();
        // iterate across the lists and remove all non assigned name objects
        return arrayList;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTask(@RequestParam String id) {
        Optional<Task> task = taskRepository.findById(id);
//        Grab the object advance the status as long as it can be advanced.
//        task.get().setStatus();
        return task.get();
    }
}