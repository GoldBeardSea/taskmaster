package com.goldbeardsea.taskmaster.controller;

import com.goldbeardsea.taskmaster.config.S3Client;
import com.goldbeardsea.taskmaster.model.Task;
import com.goldbeardsea.taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin
public class TaskController {

    private S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    // gets tasks
    @GetMapping("/tasks")
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> arrayList = (ArrayList<Task>) taskRepository.findAll();
        return arrayList;
    }

    @GetMapping("/users/{name}/tasks")
    public ArrayList<Task> getAssigneeTasks(@RequestParam String assignee){
        return taskRepository.findByAssignee(assignee);
    }

    // posts things
    @PostMapping("/tasks")
    public Task taskCreation(@RequestParam String title, @RequestParam String description, @RequestParam String assignee) {
        if (!assignee.equals("")) {
            Task newTask = new Task(title, description, "Assigned");
            newTask.setAssignee(assignee);
            taskRepository.save(newTask);
            return newTask;
        } else {
            Task newTask = new Task(title, description, "Available");
            taskRepository.save(newTask);
            return newTask;
        }

    }

    @PostMapping("/tasks/{id}/images")
    public Task uploadFile(@RequestParam("uuid") String id, @RequestPart(value = "file") MultipartFile file){

        String pic = this.s3Client.uploadFile(file);
        Optional<Task> updatingImageTask = taskRepository.findById(id);
        Task task = updatingImageTask.get();
        task.setPicUrl(pic);
        taskRepository.save(task);
        return task;
    }

    // updates tasks
    @PutMapping("/tasks/{id}/state")
    public void advanceTaskStatus(@RequestParam String id){
        Task currentTask = taskRepository.findById(id).get();
        currentTask.advanceTask();
        taskRepository.save(currentTask);
    }

    @PutMapping("/tasks/{id}/state/{assignee}")
    public void assignTask(@PathVariable String id, @PathVariable String assignee){
        Task currentTask = taskRepository.findById(id).get();
        currentTask.setAssignee(assignee);
        currentTask.advanceTask();
        taskRepository.save(currentTask);
    }

}