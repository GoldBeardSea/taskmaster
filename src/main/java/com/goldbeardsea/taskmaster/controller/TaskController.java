package com.goldbeardsea.taskmaster.controller;

import com.goldbeardsea.taskmaster.model.Task;
import com.goldbeardsea.taskmaster.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    //tasks
    @GetMapping("/tasks")
    public String getAllTasks(Model m) {
        ArrayList<Task> arrayList = (ArrayList<Task>) taskRepository.findAll();
        m.addAttribute("task", arrayList);
        return "task";
    }

    @PostMapping("/tasks")
    public void taskCreation(Model m, @RequestParam String title, @RequestParam String description) {
        Task newTask = new Task(title, description, "Available");
        taskRepository.save(newTask);

    }

}